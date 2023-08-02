const path = require('path');

const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  mode: 'development',
  devtool: 'inline-source-map',
  plugins: [
    new CopyWebpackPlugin({
      patterns: [
        {
          from: 'public/mockServiceWorker.js',
          to: 'mockServiceWorker.js',
        },
      ],
    }),
  ],
};
