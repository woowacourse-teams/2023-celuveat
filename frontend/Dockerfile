# Build stage
FROM node:18-alpine AS build

WORKDIR /app

COPY package.json yarn.lock .

RUN yarn install

COPY . .

RUN yarn build

# Final stage
FROM node:18-alpine

WORKDIR /app

COPY --from=build /app/package.json /app/yarn.lock ./
COPY --from=build /app/dist ./dist

RUN yarn install --production

CMD ["yarn", "start", "--port", "3000"]
