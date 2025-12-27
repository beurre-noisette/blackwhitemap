/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: [
          "Pretendard",
          "-apple-system",
          "BlinkMacSystemFont",
          "system-ui",
          "sans-serif",
        ],
      },

      colors: {
        // Figma 디자인 시스템 색상 팔레트
        // CSS Variables를 Tailwind 색상으로 매핑
        red: {
          50: "var(--color-red-50)",
          100: "var(--color-red-100)",
          200: "var(--color-red-200)",
          300: "var(--color-red-300)",
          400: "var(--color-red-400)",
          500: "var(--color-red-500)",
          600: "var(--color-red-600)",
          700: "var(--color-red-700)",
          800: "var(--color-red-800)",
        },
        purple: {
          50: "var(--color-purple-50)",
          100: "var(--color-purple-100)",
          200: "var(--color-purple-200)",
          300: "var(--color-purple-300)",
          400: "var(--color-purple-400)",
          500: "var(--color-purple-500)",
          600: "var(--color-purple-600)",
          700: "var(--color-purple-700)",
          800: "var(--color-purple-800)",
          900: "var(--color-purple-900)",
        },
        gray: {
          100: "var(--color-gray-100)",
          200: "var(--color-gray-200)",
          300: "var(--color-gray-300)",
          400: "var(--color-gray-400)",
          500: "var(--color-gray-500)",
          600: "var(--color-gray-600)",
          700: "var(--color-gray-700)",
          800: "var(--color-gray-800)",
          900: "var(--color-gray-900)",
        },
        white: "var(--color-white)",
        black: "var(--color-black)",
      },
    },
  },
  plugins: [],
};
