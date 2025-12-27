import { API_CONFIG } from "./config";

export class ApiError extends Error {
  constructor(
    message: string,
    public status?: number,
    public statusText?: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

/**
 * Fetch with timeout
 */
async function fetchWithTimeout(
  url: string,
  options: RequestInit = {},
  timeout: number = API_CONFIG.TIMEOUT,
): Promise<Response> {
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), timeout);

  try {
    const response = await fetch(url, {
      ...options,
      signal: controller.signal,
      headers: {
        ...API_CONFIG.HEADERS,
        ...options.headers,
      },
    });

    clearTimeout(timeoutId);
    return response;
  } catch (error) {
    clearTimeout(timeoutId);

    if (error instanceof Error) {
      if (error.name === "AbortError") {
        throw new ApiError("Request timeout");
      }
      throw new ApiError(`Network error: ${error.message}`);
    }

    throw new ApiError("Unknown error occurred");
  }
}

/**
 * GET request
 */
export async function get<T>(endpoint: string): Promise<T> {
  const url = `${API_CONFIG.BASE_URL}${endpoint}`;

  const response = await fetchWithTimeout(url, {
    method: "GET",
  });

  if (!response.ok) {
    throw new ApiError(
      `GET ${endpoint} failed`,
      response.status,
      response.statusText,
    );
  }

  return response.json();
}

/**
 * POST request
 */
export async function post<T, D = unknown>(
  endpoint: string,
  data: D,
): Promise<T> {
  const url = `${API_CONFIG.BASE_URL}${endpoint}`;

  const response = await fetchWithTimeout(url, {
    method: "POST",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new ApiError(
      `POST ${endpoint} failed`,
      response.status,
      response.statusText,
    );
  }

  return response.json();
}
