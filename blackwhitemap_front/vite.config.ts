import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    // @ 경로를 src 디렉토리로 매핑 (tsconfig.json의 paths와 동일)
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
})
