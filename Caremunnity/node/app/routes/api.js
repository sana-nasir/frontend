var multer = require('multer');
var path = require('path');
var uploadFolder = "./public/images"
var User = require('../models/user');
var Post = require('../models/post');
var Goal = require('../models/goal');
var Medication = require('../models/medication');
var Notify = require('../models/notification');

module.exports = function(router) {

    router.post('/login', function(req, res) {
        User.authenticate(req.body.username, req.body.password, function(err, result) {
            if(result.length == 1 && result[0].status == 2) {
                User.login(result[0].user_id, function(err) {
                    if (err) throw err;
                });
                res.json({'success': 'true', 'user_id':result[0].user_id, 'permissions':result[0].permissions});
            } else {
                if(result[0].status == 1) {
                    res.json({'success': 'false', 'message':'user already logged in'});
                } else {
                    res.json({'success': 'false', 'message':'user not found'});
                }
            }
        });
    });

    router.post('/logout', function(req, res) {
        if(typeof req.body.user_id != "number") {
            res.json({'success': 'false', 'message':'please valid enter user id'});
        }
        User.logout(req.body.user_id, function(err) {
            if (err) throw err;
            if(req.body.user_id) {
                res.json({'success': 'true'});
            } else {
                res.json({'success': 'false', 'message':'enter user id'});
            }
        });
    });

    router.post('/findfriends', function(req, res) {
        User.findFriends(req.body.user_id, req.body.first, req.body.last, function(err, result) {
            if (err) throw err;
            if(result[0]) {
                res.json({'success': 'true', users: result});
            } else {
                res.json({'success': 'false', 'message':'no users found'});
            }
        });
    });

    router.post('/addfriend', function(req, res) {
        User.checkFollowing(req.body.user_id, req.body.friend_id, function(err, result) {
            for(key in result[0]) {
                if(result[0][key] != 1) {
                    User.addFriend(req.body.user_id, req.body.friend_id, function(err, row) {
                        if (err) throw err;
                        if(row) {
                            res.json({'success': 'true', 'id': row.insertId});
                        } else {
                            res.json({'success': 'false', 'message':'did not add friend'});
                        }
                    });
                } else {
                    res.json({'success': 'false', 'message':'user already following'});
                }
            }
        });
    });

    router.delete('/friend', function(req, res) {
        User.checkFollowing(req.body.user_id, req.body.friend_id, function(err, result) {
            for(key in result[0]) {
                if(result[0][key] == 1) {
                    User.removeFriend(req.body.user_id, req.body.friend_id, function(err, row) {
                        if (err) throw err;
                        if(row[0]) {
                            res.json({'success': 'true'});
                        } else {
                            res.json({'success': 'false', 'message':'did not remove friend'});
                        }
                    });
                } else {
                    res.json({'success': 'false', 'message':'user not following'});
                }
            }
        });
    });

    router.get('/friends/:id', function(req, res) {
        User.getFriends(req.params.id, function(err, result) {
            if (err) throw err;
            if(result[0]) {
                res.json(result);
            } else {
                res.json({'success': 'false', 'message':'no friends found'});
            }
        });
    });

    router.post('/post', function(req, res) {
        Post.createPost(req.body.user_id, req.body.content, req.body.goal_id, function(err, result) {
            if (err) throw err;

            if(result.affectedRows > 0) {
                res.json({'success': 'true', 'id': result.insertId});
            } else {
                res.json({'success': 'false', 'message':'post not created'});
            }
        });
    });

    router.delete('/post', function(req, res) {
        Post.postExists(req.body.post_id, function(err, result) {
            if (err) throw err;

            for(key in result[0]) {
                if(result[0][key] == 1) {
                    Post.deletePost(req.body.post_id, function(err, row) {
                        if(row.affectedRows > 0) {
                            res.json({'success': 'true'});
                        } else {
                            res.json({'success': 'false', 'message':'post not deleted'});
                        }
                    });
                } else {
                    res.json({'success': 'false', 'message':'post does not exist'});
                }
            }
        });
    });

    router.get('/posts/:id', function(req, res) {
        Post.getPosts(req.params.id, function(err, result) {
            if (err) throw err;
            if(result[0]) {
                res.json({'success': 'true', posts: result});
            } else {
                res.json({'success': 'false', 'message':'no posts found'});
            }
        });
    });

    router.post('/like', function(req, res) {
        Post.likePost(req.body.user_id, req.body.post_id, function(err, result) {
            if (err) throw err;

            if(result.affectedRows > 0) {
                res.json({'success': 'true'});
            } else {
                res.json({'success': 'false', 'message':'like failed'});
            }
        });
    });

    router.post('/unlike', function(req, res) {
        Post.unLikePost(req.body.user_id, req.body.post_id, function(err, result) {
            if (err) throw err;

            if(result.affectedRows > 0) {
                res.json({'success': 'true'});
            } else {
                res.json({'success': 'false', 'message':'unlike failed'});
            }
        });
    });

    router.get('/goals', function(req, res) {
        Goal.getGoals(function(err, result) {
            if (err) throw err;
            
            if(result[0]) {
                res.json({'success': 'true', 'goals': result});
            } else {
                res.json({'success': 'false', 'message':'no goals found'});
            }
        });
    });

    router.get('/usergoals/:id', function(req, res) {
        Goal.getUserGoals(req.params.user_id, function(err, result) {
            if (err) throw err;
            
            if(result[0]) {
                res.json({'success': 'true', 'goals': result});
            } else {
                res.json({'success': 'false', 'message':'no goals found'});
            }
        });
    });

    router.post('/incrementgoal', function(req, res) {
        Goal.checkGoalExists(req.body.user_id, req.body.goal_id, function(err, result) {
            if (err) throw err;

            for(key in result[0]) {
                if(result[0][key] == 1) {
                    Goal.incrementGoal(req.body.user_id, req.body.goal_id, function(err, row) {
                        if(row.affectedRows > 0) {
                            res.json({'success': 'true'});
                        } else {
                            res.json({'success': 'false', 'message':'goal not incremented'});
                        }
                    });
                } else {
                    res.json({'success': 'false', 'message':'post does not exist'});
                }
            }
        });
    });

    router.post('/incrementaccomplishment', function(req, res) {
        Goal.checkGoalExists(req.body.user_id, req.body.goal_id, req.body.trophy_id, function(err, result) {
            if (err) throw err;

            for(key in result[0]) {
                if(result[0][key] == 1) {
                    Goal.incrementAccomplishment(req.body.user_id, req.body.goal_id, req.body.trophy_id, function(err, row) {
                        if(row.affectedRows > 0) {
                            res.json({'success': 'true'});
                        } else {
                            res.json({'success': 'false', 'message':'goal not incremented'});
                        }
                    });
                } else {
                    res.json({'success': 'false', 'message':'goal does not exist'});
                }
            }
        });
    });

    router.get('/accomplishments/:id', function(req, res) {
        Goal.getAccomplishments(req.params.id, function(err, result) {
            if (err) throw err;

            if(result[0]) {
                res.json({'success': 'true', 'accomplishments': result});
            } else {
                res.json({'success': 'false', 'message':'no acommplishments found'});
            }
        });
    });

    router.get('/accomplishments/:id', function(req, res) {
        Goal.getAccomplishments(req.params.id, function(err, result) {
            if (err) throw err;

            if(result[0]) {
                res.json({'success': 'true', 'accomplishments': result});
            } else {
                res.json({'success': 'false', 'message':'no acommplishments found'});
            }
        });
    });

    router.get('/medication/:id', function(req, res) {
        Medication.getMedication(req.params.id, function(err, result) {
            if (err) throw err;
            
            if(result[0]) {
                res.json({'success': 'true', 'medication': result});
            } else {
                res.json({'success': 'false', 'message':'no medication found'});
            }
        })
    });

    router.get('/medications/:id', function(req, res) {
        Medication.getUserMedications(req.params.id, function(err, result) {
            if (err) throw err;
            
            if(result[0]) {
                res.json({'success': 'true', 'medication': result});
            } else {
                res.json({'success': 'false', 'message':'no medication found'});
            }
        });
    });

    router.post('/notification', function(req, res) {
        Notify.createNotification(req.body.user_id, req.body.goal_id, req.body.medication_id, req.body.title, req.body.start_day, req.body.end_day, req.body.alert_sunday, req.body.alert_monday, req.body.alert_tuesday, req.body.alert_wednesday, req.body.alert_thursday, req.body.alert_friday, req.body.alert_saturday, req.body.alert, function(err, result) {
            if (err) throw err;
            
            if(result.affectedRows > 0) {
                Goal.checkGoalExists(req.body.user_id,req.body.goal_id, function(err, row){
                    if (err) throw err;

                    for(key in row[0]) {
                        if(row[0][key] != 1) {
                            Goal.createGoal(req.body.user_id,req.body.goal_id, function(err, gal) {
                                if (err) throw err;

                                if(gal.insertId) {
                                    res.json({'success': 'true', 'id': result.insertId});
                                } else {
                                    res.json({'false': 'true', 'message':'user goal not created'});
                                }
                            });
                        } else {
                            res.json({'success': 'true', 'id': result.insertId});
                        }
                    }
                });
            } else {
                res.json({'success': 'false', 'message':'notification not created'});
            }
        });
    });

    router.delete('/notification', function(req, res) {
        Notify.createNotification(req.body.notification_id, function(err, result) {
            if (err) throw err;
            
            if(result.affectedRows > 0) {
                res.json({'success': 'true'});
            } else {
                res.json({'success': 'false', 'message':'notification not deleted'});
            }
        });
    });

    router.post('/editnotification', function(req, res) {
        Notify.editNotification(req.body.notification_id, req.body.user_id, req.body.goal_id, req.body.medication_id, req.body.title, req.body.start_day, req.body.end_day, req.body.alert_sunday, req.body.alert_monday, req.body.alert_tuesday, req.body.alert_wednesday, req.body.alert_thursday, req.body.alert_friday, req.body.alert_saturday, req.body.alert, function(err, result) {
            if (err) throw err;
            
            if(result.affectedRows > 0) {
                Goal.checkGoalExists(req.body.user_id,req.body.goal_id, function(err, row){
                    if (err) throw err;

                    for(key in row[0]) {
                        if(row[0][key] != 1) {
                            Goal.createGoal(req.body.user_id,req.body.goal_id, function(err, gal) {
                                if (err) throw err;

                                if(gal.insertId) {
                                    res.json({'success': 'true', 'message':'notification updated'});
                                } else {
                                    res.json({'false': 'true', 'message':'user goal not created'});
                                }
                            });
                        } else {
                            res.json({'success': 'true', 'message':'notification updated'});
                        }
                    }
                });
            } else {
                res.json({'success': 'false', 'message':'notification not updated'});
            }
        });
    });

    router.get('/notification/:id', function(req, res) {
        Notify.getNotification(req.params.id, function(err, result) {
            if (err) throw err;

            if(result[0]) {
                res.json({'success': 'true', 'notifications': result});
            } else {
                res.json({'success': 'false', 'message':'no notifications found'});
            }
        });
    });

    router.get('/firstnotification/:id', function(req, res) {
        Notify.getFirstNotification(req.params.id, function(err, result) {
            if (err) throw err;
            
            if(result) {
                res.json({'success': 'true', 'notifications': result});
            } else {
                res.json({'success': 'false', 'message':'no notifications today'});
            }
        });
    });

    router.post('/notifications', function(req, res) {
        Notify.getNotifications(req.body.user_id, req.body.date, function(err, result) {
            if (err) throw err;
            
            if(result[0]) {
                res.json({'success': 'true', 'notifications': result});
            } else {
                res.json({'success': 'false', 'message':'no notifications found'});
            }
        });
    });

    var newFile;
    var storage = multer.diskStorage({
        destination: function(req, file, callback) {
            callback(null, uploadFolder)
        },
        filename: function(req, file, callback) {
            newFile = file.fieldname +'-'+ Date.now() + path.extname(file.originalname);
            callback(null, newFile)
        }
    });

    var upload = multer({storage: storage,
        fileFilter: function(req, file, callback) {
            var ext = path.extname(file.originalname)
            if (ext !== '.png' && ext !== '.jpg' && ext !== '.gif' && ext !== '.jpeg') {
                return callback(res.end('Only images are allowed'), null)
            }
            callback(null, true)
        }
    });

    router.post('/upload', upload.single('avatar'), function(req, res) {
        console.log(req.body.user_id);
        res.end("done");
    })

    return router;
}