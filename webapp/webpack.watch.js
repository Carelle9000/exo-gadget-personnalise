const config = require('./webpack.prod.js');

config.mode = 'development';
config.watch = true;
config.devtool = 'inline-source-map';

module.exports = config;
