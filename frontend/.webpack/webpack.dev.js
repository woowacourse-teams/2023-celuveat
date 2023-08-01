const path = require('path');

const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  mode: 'development',
  devtool: 'inline-source-map',
  devServer: {
    static: {
      directory: path.resolve(__dirname, '../public'),
    },
    hot: true,
    open: true,
    historyApiFallback: true,
    allowedHosts: 'all',
  },
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
