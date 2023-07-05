const { merge } = require('webpack-merge');

const commonConfig = require('./webpack.common.js');
const productionConfig = require('./webpack.prod.js');
const developmentConfig = require('./webpack.dev.js');

module.exports = (env, args) => {
  switch (args.mode) {
    case 'development':
      return merge(commonConfig, developmentConfig);
    case 'production':
      return merge(commonConfig, productionConfig);
    default:
      throw new Error('No matching configuration was found!');
  }
};
