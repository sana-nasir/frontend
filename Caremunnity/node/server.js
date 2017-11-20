var express             = require('express');
var app                 = express();
var server              = require('http').createServer(app);  
var port                = process.env.PORT || 4000;
var morgan              = require('morgan');
var request             = require('request');
var mysql               = require('mysql');
var bodyParser          = require('body-parser');
var router              = express.Router();
var appRoutes           = require('./app/routes/api')(router);

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(__dirname + '/public'));
app.use('/api', appRoutes);

server.listen(port, function() {
	console.log('Running the Server on port ' + port);
});
