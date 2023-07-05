const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

const { EnvironmentPlugin, DefinePlugin } = require('webpack');

module.exports = {
  mode: process.env.NODE_ENV,
  entry: ['./src/index.tsx'],
  output: {
    path: path.resolve(__dirname, 'dist/'),
    publicPath: '/',
    clean: true,
  },
  resolve: {
    module: ['node_modules'],
    extensions: ['.tsx', '.ts', '.jsx', '.js', 'json'],
  },
  module: {
    rules: [
      {
        test: /\.(ts|tsx)$/,
        use: [
          'babel-loader',
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true,
            },
          },
        ],
        exclude: /node_modules/,
      },
      {
        test: /\.(jpg|jpeg|gif|png|svg|ico)?$/,
        type: 'asset',
        generator: {
          filename: 'images/[name].[ext]',
        },
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
      filename: 'index.html',
    }),
    new ForkTsCheckerWebpackPlugin(),
    new DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV),
    }),
    new EnvironmentPlugin(['NODE_ENV']),
  ],
  devtool: 'inline-source-map',
  devServer: {
    static: {
      directory: path.resolve(__dirname, 'public'),
    },
    hot: true,
    open: true,
    historyApiFallback: true,
  },
};
