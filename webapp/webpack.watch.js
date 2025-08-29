const path = require('path');
const {merge} = require('webpack-merge');
const webpackCommonConfig = require('./webpack.prod.cjs');

// the display name of the war
const app = 'webapp';

// add the server path to your server location path
const exoServerPath = "/opt/exo";

let config = merge(webpackCommonConfig, {
    mode: 'development',
  output: {
    path: path.resolve(`${exoServerPath}/webapp/webapp`)
  },
  devtool: 'inline-source-map'
});

module.exports = config;