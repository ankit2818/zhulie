var express = require('express')
const mongoose = require('mongoose')
var app = express()
var bodyParser = require('body-parser')
// routes
var user = require('./routes/user.js')
var team = require('./routes/team.js')
// db coonection
var mongoDB = 'mongodb://localhost:27017/zhulie'
mongoose.connect(mongoDB, { useNewUrlParser: true })
var db = mongoose.connection

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true, limit: '50mb' }))

app.use(express.static(__dirname + '/public'))
// app.use('/profile', express.static(__dirname + '/public/profile'))

app.use('/user', user)
app.use('/team', team)

app.listen(5656)
