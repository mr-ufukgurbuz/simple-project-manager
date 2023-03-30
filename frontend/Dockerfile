# << STAGE 1 >>
FROM ftp.company.com.tr:8082/node:14-alpine AS build-step
ENV NODE_ENV production
# Sets COMPANY npm repository address
COPY .npmrc /root/.npmrc
# Add a work directory
RUN mkdir /app
# Change working directory
WORKDIR /app
# Cache and Install dependencies
COPY package.json .
COPY package-lock.json .
RUN npm install
# Copy app files
COPY . .
# Build the app
RUN npm run build


# << STAGE 2 >> 
# Bundle static assets with nginx
FROM ftp.company.com.tr:8082/nginx:1.21.0-alpine as production
ENV NODE_ENV production
# Copy built assets from build-step
COPY --from=build-step /app/build /usr/share/nginx/html
# Add your nginx.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Expose port
EXPOSE 80
# Start nginx
CMD ["nginx", "-g", "daemon off;"]