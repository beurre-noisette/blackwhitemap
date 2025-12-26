export const API_CONFIG = {
  BASE_URL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",

  TIMEOUT: 10000,

  HEADERS: {
    "Content-Type": "application/json",
  },
} as const;
