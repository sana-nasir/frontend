var db = require('../../db');

var Goal = {  
    getGoals: function(callback) {
        return db.query('Select * goals', callback);  
    },
    getUserGoals: function(user_id, callback) {
        return db.query('select goals.goal_id, goals.title, goals.picture, user_goals.goal_count from user_goals left join goals on user_goals.goal_id = goals.goal_id where user_goals.user_id = ?'
        ,[user_id], callback)
    },
    checkGoalExists: function(user_id, goal_id, callback) {
        return db.query('select exists (select * from user_goals where user_goals.goal_id = ? and user_goals.user_id = ?)'
        ,[goal_id, user_id], callback)
    },
    createGoal: function(user_id, goal_id, callback) {
        return db.query('insert into user_goals (goal_id, user_id, goal_count, gold_count, silver_count, bronze_count) values (?, ?, 0, 0, 0, 0)'
        ,[goal_id, user_id], callback)
    },
    incrementGoal: function(user_id, goal_id, callback) {
        return db.query('update user_goals set goal_count = goal_count +1 where user_goals.user_id = ? and user_goals.goal_id = ?'
        ,[user_id, goal_id], callback)
    },
    incrementAccomplishment: function(user_id, goal_id, trophy_id, callback) {
        if(trophy_id == 1) {
            return db.query('update user_goals set gold_count = gold_count +1 where goal_id = ? and user_id = ?'
            ,[goal_id, user_id], callback)
        } else if(trophy_id == 2) {
            return db.query('update user_goals set silver_count = silver_count +1 where goal_id = ? and user_id = ?'
            ,[goal_id, user_id], callback)
        } else if(trophy_id == 3) {
            return db.query('update user_goals set bronze_count = bronze_count +1 where goal_id = ? and user_id = ?'
            ,[goal_id, user_id], callback)
        }
    },
    getAccomplishments: function(user_id) {
        return db.query('select goals.goal_id, goals.title, goals.picture, user_goals.gold_count, user_goals.silver_count, user_goals.bronze_count from user_goals left join goals on user_goals.goal_id = goals.goal_id where user_goals.user_id = ?'
        ,[user_id], callback)
    }
};  
module.exports = Goal;