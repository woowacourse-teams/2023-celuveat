const path = require('path');
const Dotenv = require('dotenv-webpack');
const Dotenv = require('dotenv-webpack');
const commonConfig = require('./webpack.common.js');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

const commonPlugins = [
  new HtmlWebpackPlugin({
    template: './public/index.html',
    filename: 'index.html',
  }),
  new ForkTsCheckerWebpackPlugin(),
  new MiniCssExtractPlugin({
    filename: 'fonts/font.css',
  }),
];

const commonRules = [
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
  new MiniCssExtractPlugin({
    filename: 'fonts/font.css',
  }),
];

module.exports = (env, args) => {
  const { TARGET_ENV } = env;

  const isProd = args.mode === 'production';

  return {
    mode: args.mode,
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
    ],
  };
};
