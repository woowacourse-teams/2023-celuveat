const path = require('path');
const Dotenv = require('dotenv-webpack');
const commonConfig = require('./webpack.common.js');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const RefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

const commonPlugins = [
  new HtmlWebpackPlugin({
    template: './public/index.html',
    filename: 'index.html',
  }),
  new ForkTsCheckerWebpackPlugin(),
  new MiniCssExtractPlugin({
    filename: 'fonts/font.css',
  }),
  // new BundleAnalyzerPlugin({
  //   analyzerMode: 'static',
  // }),
];

const commonRules = [
  {
    test: /\.(ts|tsx)$/,
    use: ['babel-loader'],
    exclude: /node_modules/,
  },
  {
    test: /\.(jpg|jpeg|gif|png|ico)?$/,
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
];

module.exports = (env, args) => {
  const { TARGET_ENV } = env;

  return {
    mode: args.mode,
    ...commonConfig,
    module: {
      rules: [
        ...commonRules,
        {
          test: /\.css$/i,
          use: [MiniCssExtractPlugin.loader, 'css-loader'],
          sideEffects: true,
        },
      ],
    },
    plugins: [
      ...commonPlugins,
      new Dotenv({
        path: path.resolve(__dirname, `../.${TARGET_ENV}.env`),
      }),
      new RefreshWebpackPlugin({}),
    ],
  };
};
