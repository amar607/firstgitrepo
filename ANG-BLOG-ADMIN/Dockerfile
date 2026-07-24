# Step 1: Build Angular app
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Step 2: Serve with Nginx
FROM nginx:1.25
COPY --from=build /app/dist/ang-blog-admin /usr/share/nginx/html

# Copied as a template - nginx's own entrypoint runs envsubst on it at
# container start, substituting ${PORT} with the port Railway assigns,
# then writes the result to /etc/nginx/conf.d/default.conf.
COPY nginx.conf /etc/nginx/templates/default.conf.template

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
