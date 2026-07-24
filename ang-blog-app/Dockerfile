# Step 1: Build Angular app
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Step 2: Serve with Nginx
FROM nginx:1.25
COPY --from=build /app/dist/ang-blog-app /usr/share/nginx/html

# Copied as a *template*, not straight to conf.d - the official nginx image's
# entrypoint runs envsubst on every file here at container start, filling in
# ${PORT} with whatever port Railway assigns, then writes the result to
# /etc/nginx/conf.d/default.conf. This is why plain "listen 4200" doesn't
# work on Railway: the port Railway gives you is only known at runtime.
COPY nginx.conf /etc/nginx/templates/default.conf.template

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

#docker build -t angular-app .
#docker run -itd -p 4200:4200 angular-app
