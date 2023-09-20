const path = require('path');
const commonConfig = require('./webpack.common.js');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const Dotenv = require('dotenv-webpack');

const commonPlugins = [
  new HtmlWebpackPlugin({
    template: './public/index.html',
    filename: 'index.html',
  }),
  new ForkTsCheckerWebpackPlugin(),
];

module.exports = (env, args) => {
  const { TARGET_ENV } = env;

  return {
    ...commonConfig,
    plugins: [
      ...commonPlugins,
      new Dotenv({
        path: path.resolve(__dirname, `../.${TARGET_ENV}.env`),
      }),
    ],
  };
};
