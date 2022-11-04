/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'navbar' : '#1e1e2d',
        'body' : '#151521',
      },
    },
  },
  plugins: [],
}
