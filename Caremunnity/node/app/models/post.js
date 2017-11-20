var db = require('../../db');

var Post = {  
    createPost: function(user_id, content, goal_id, callback) {
        return db.query('insert into posts (user_id, content, goal_id) values (?, ?, ?)',[user_id, content, goal_id], callback);  
    },
    postExists: function(post_id, callback) {
        return db.query('select exists(select * from posts where post_id = ?)',[post_id], callback);  
    },
    deletePost: function(post_id, callback) {
        return db.query('delete from posts where posts.post_id = ?',[post_id], callback);  
    },
    getPosts: function(user_id, callback) {
        return db.query('select users.user_id, users.first, users.last, posts.post_id, posts.content, goals.title, goals.picture, COUNT(likes.like_id) as likes from relationship inner join users on users.user_id = relationship.user_2_id inner join posts on posts.user_id = relationship.user_2_id inner join goals on goals.goal_id = posts.goal_id left join likes on likes.post_id = posts.post_id where relationship.user_1_id = ? group by users.user_id'
        ,[user_id], callback);  
    },
    likePost: function(post_id, user_id, callback) {
        return db.query('insert into likes (post_id, user_id) values (?, ?)',[post_id, user_id], callback);  
    },
    unLikePost: function(post_id, user_id, callback) {
        return db.query('delete from likes where likes.post_id = ? and likes.user_id = ?',[post_id, user_id], callback);  
    }
};  
module.exports = Post;