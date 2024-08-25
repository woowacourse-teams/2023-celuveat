const path = require('path');
const Dotenv = require('dotenv-webpack');
const { merge } = require('webpack-merge');

const common = require('./webpack.common');

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval-cheap-module-source-map',
  cache: {
    type: 'filesystem',
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx|ts|tsx)$/i,
        exclude: /node_modules/,
        loader: 'babel-loader',
        options: {
          cacheCompression: false,
          cacheDirectory: true,
          presets: ['@babel/preset-env', ['@babel/preset-react', { runtime: 'automatic' }], '@babel/preset-typescript'],
          plugins: [
            ['babel-plugin-styled-components'],
            [
              'babel-plugin-root-import',
              {
                rootPathPrefix: '~',
                rootPathSuffix: 'src',
              },
            ],
          ],
        },
      },
    ],
  },
  plugins: [
    new Dotenv({
      path: path.resolve(__dirname, `../.env.dev`),
    }),
  ],
});
