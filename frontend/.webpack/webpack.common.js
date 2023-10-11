const path = require('path');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');

module.exports = {
  entry: ['./src/index.tsx'],
  output: {
    path: path.resolve(__dirname, '../dist/'),
    publicPath: '/',
    filename: '[name].[chunkhash:8].js',
    clean: true,
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js', '.json'],
  },

  devServer: {
    static: {
      directory: path.resolve(__dirname, '../public'),
    },
    hot: true,
    open: true,
    historyApiFallback: true,
    allowedHosts: 'all',
  },

  optimization: {
    minimizer: ['...', new CssMinimizerPlugin()],
  },

  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
};
