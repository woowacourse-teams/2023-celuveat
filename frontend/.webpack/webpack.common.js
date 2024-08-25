const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const FaviconsWebpackPlugin = require('favicons-webpack-plugin');

module.exports = {
  entry: './src/index.tsx',

  output: {
    publicPath: '/',
    path: path.join(__dirname, '../dist'),
    filename: '[name].[chunkhash].js',
    clean: true,
  },

  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },

  module: {
    rules: [
      {
        test: /\.css$/i,
        use: [MiniCssExtractPlugin.loader, 'css-loader'],
        sideEffects: true,
      },
      {
        test: /\.(jpg|jpeg|gif|png|ico|webp)?$/,
        type: 'asset',
        generator: {
          filename: 'images/[name][ext]',
        },
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)?$/,
        type: 'asset',
        generator: {
          filename: 'fonts/[name][ext]',
        },
      },
      {
        test: /\.svg$/,
        use: ['@svgr/webpack'],
      },
    ],
  },

  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
      filename: 'index.html',
    }),
    new MiniCssExtractPlugin(),
    new FaviconsWebpackPlugin({ logo: 'public/logo.png', manifest: 'public/manifest.json' }),
  ],

  devServer: {
    static: {
      directory: path.resolve(__dirname, '../public'),
    },
    hot: true,
    open: true,
    historyApiFallback: true,
    allowedHosts: 'all',
  },

  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
};
