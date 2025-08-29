const path = require('path');
const ESLintPlugin = require('eslint-webpack-plugin');
const { VueLoaderPlugin } = require('vue-loader');

// the display name of the war
const app = 'webapp';

const config = {
  mode: 'production', 
  context: path.resolve(__dirname, '.'),
  entry: {
    exogadgettApp: './src/main/webapp/vue-app/main.js'
  },
  output: {
    path: path.resolve(__dirname, './src/main/webapp'),
    filename: 'js/[name].bundle.js', // Ce nom sera référencé par eXo
    libraryTarget: 'amd'
  },
  externals: {
    //vue: 'Vue',
    vuetify: 'Vuetify',
    jquery: '$',
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      }
    ]
  },
  plugins: [
      new ESLintPlugin({
      files: [
        './src/main/webapp/vue-app/*.js',
        './src/main/webapp/vue-app/*.vue',
        './src/main/webapp/vue-app/**/*.js',
        './src/main/webapp/vue-app/**/*.vue',
      ],
    }),
    new VueLoaderPlugin()
  ]

};
module.exports = config;
