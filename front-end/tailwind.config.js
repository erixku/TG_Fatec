/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  presets: [require("nativewind/preset")],
  theme: {
    extend: {
      fontFamily: {
        //Nunito
        'nunito-light': ['Nunito-Light'],
        'nunito': ['Nunito-Regular'],
        'nunito-semibold': ['Nunito-SemiBold'],
        'nunito-bold': ['Nunito-Bold'],

        //Playwrite
        'hand-thin': ['Playwrite-Thin'],
        'hand-extralight': ['Playwrite-ExtraLight'],
        'hand-light': ['Playwrite-Light'],
        'hand': ['Playwrite-Regular'],
      }
    },
  },
  future: {
    hoverOnlyWhenSupported: true,
  },
  plugins: [],
};
