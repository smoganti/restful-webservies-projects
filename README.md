# restful-webservies-projects
Restful Webservices based repository folder with projects
- restful-socialnw : Includes a ConcurrentHashMap based implementation of a User and Social network posts implementation, while the tomcat port be adjusted at `server.port` in the application properties.
    - User mappings
        - ` /user/getUsers`       - retrieves all the users from the existing map
        - `/user/{id}`            - returns a user for the specified {id}       
        - `/user/getUsers`             - used to post a map of users to the existing server
        - `/user`                 - used to post a specific user details to the existing server
    - Post mappings
        - ` /post/getPosts`       - retrieves all the posts from the existing map
        - `/post/{id}`            - returns a post for the specified {id}       
        - `/post/getPosts`             - used to publish a map of posts to the existing server
        - `/post`                 - used to publish a specific post details to the existing server
    - SpringKafka Integration
        - This project is integrated with spring kafka framework. For the project to be run, a kafka broker needs to be running in the localhost:9092 port on a zookeeper instance at localhost:2181, while this can be configured in the application properties accordingly.
        - A submitted post or posts and user or users, will be updated under the topics `posts` and `users` respectively.
- restful-socialnw-kafkaconsumer : Includes a kafka consumer that listens to the topics : `users` or `posts`, from the restful-socialnw project in the current repository.
    - This is an association of SpringKafka Integration with spring boot 