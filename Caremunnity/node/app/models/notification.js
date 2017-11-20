var dateTime = require('node-datetime');
var db = require('../../db');

function getDayOfWeek(date) {
    var dayOfWeek = new Date(date).getDay();    
    return isNaN(dayOfWeek) ? null : ['alert_sunday', 'alert_monday', 'alert_tuesday', 'alert_wednesday', 'alert_thursday', 'alert_friday', 'alert_saturday'][dayOfWeek];
}

var Notify = {  
    createNotification: function(user_id, goal_id, medication_id, title, start_day, end_day, sunday, monday, tuesday, wednesday, thursday, friday, saturday, alert, callback) {
        return db.query('insert into notifications (user_id, goal_id, medication_id, title, start_day, end_day, alert_sunday, alert_monday, alert_tuesday, alert_wednesday, alert_thursday, alert_friday, alert_saturday, alert) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'
        ,[user_id, goal_id, medication_id, title, start_day, end_day, sunday, monday, tuesday, wednesday, thursday, friday, saturday, alert], callback);  
    },
    deleteNotification: function(notification_id, callback) {
        return db.query('delete from notfication where notification_id = ?'
        ,[notification_id], callback);
    },
    editNotification: function(notification_id, user_id, goal_id, medication_id, title, start_day, end_day, sunday, monday, tuesday, wednesday, thursday, friday, saturday, alert, callback) {
        return db.query('update notifications set user_id = ? and goal_id = ? and medication_id = ? and title = ? and start_day = ? and end_day = ? and alert_sunday = ? and alert_monday = ? and alert_tuesday = ? and alert_wednesday = ? and alert_thursday = ? and alert_friday = ? and alert_saturday = ? and alert = ? where notification_id = ?'
        ,[user_id, goal_id, medication_id, title, start_day, end_day, sunday, monday, tuesday, wednesday, thursday, friday, saturday, alert, notification_id], callback);  
    },
    getNotification: function(notification_id, callback) {
        return db.query('select * from notifications where notification_id = ?'
        ,[notification_id], callback);  
    },
    getFirstNotification: function(user_id, callback) {
        var n = getDayOfWeek(new Date());
        return db.query(`select notification_id, title, alert from notifications where user_id = ? and start_day <= curdate() and end_day >= curdate() and ${n} = '1' ORDER BY alert LIMIT 1`
        ,[user_id], callback);  
    },
    getNotifications: function(user_id, date, callback) {
        var n = getDayOfWeek(date);
        return db.query(`select notification_id, title, alert from notifications where user_id = ? and start_day <= curdate() and end_day >= curdate() and ${n} = '1' ORDER BY alert`
        ,[user_id], callback);
    }
};  
module.exports = Notify;