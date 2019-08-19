var express = require('express')
var router = express.Router()
var mongoose = require('mongoose')
var teamSchema = require('../models/teams.js')

router.post('/new', (req, res) => {
  var team = teamSchema({
    'team_name': req.body.team_name,
    'admin': req.body.email_id
  })
  team.save((err, team) => {
    console.log(team)
    if (err) throw err
    var users = mongoose.model('users')
    users.findOne({ 'email_id': req.body.email_id }, (err, user) => {
      newTeam = { 'team_id': team._id, 'team_name': team.team_name }
      user.teams.push(newTeam)
      user.save((err, updatedUser) => {
        res.json({ success: 'true', team: team })
      })
    })
  })
})

router.post('/get', (req, res) => {
  var users = mongoose.model('users')
  users.findOne({ 'email_id': req.body.email_id }, (err, user) => {
    if (err) throw err
    res.json({ success: 'true', teams: user.teams })
  })
})

router.post('/invite', (req, res) => {
  var users = mongoose.model('users')
  users.findOne({ 'email_id': req.body.Invite_email_id }, (err, user) => {
    if (err) throw err
    var invite = { 'team_id': req.body.team_id, 'admin': req.body.email_id, 'team_name': req.body.team_name }
    user.invites.push(invite)
    user.save((err, updatedUser) => {
      if (err) throw err
      res.json({ success: 'true', message: 'Invite sent!' })
    })
  })
})

router.post('/get/invites', (req, res) => {
  var users = mongoose.model('users')
  users.findOne({ 'email_id': req.body.email_id }, (err, user) => {
    if (err) throw err
    res.json({ success: 'true', invites: user.invites })
  })
})

router.post('/join', (req, res) => {
  var users = mongoose.model('users')
  var teams = mongoose.model('teams')
  teams.findOne({ 'email_id': req.body.team_id }, (err, team) => {
    if (err) throw err
    team.team_members.push(req.body.email_id)
    users.findOne({ 'email_id': req.body.email_id }, (err, user) => {
      if (err) throw err
      user.teams.push(req.body.team_id)
      res.json({ success: 'true', message: 'Invite Accepted!' })
    })
  })
})

router.post('/members', (req, res) => {
  var teams = mongoose.model('teams')
  teams.findOne({ 'email_id': req.body.team_id }, (err, team) => {
    if (err) throw err
    res.json({ success: 'true', team: team.team_members })
  })
})

router.post('/members/remove', (req, res) => {
  var users = mongoose.model('users')
  var teams = mongoose.model('teams')
  teams.findOne({ 'email_id': req.body.team_id }, (err, team) => {
    if (err) throw err
    team.team_members.pull(req.body.member_email_id)
    users.findOne({ 'email_id': req.body.member_email_id }, (err, user) => {
      if (err) throw err
      user.teams.pull(req.body.team_id)
      res.json({ success: 'true', message: 'Invite Accepted!' })
    })
  })
})

router.post('/project/new', (req, res) => {
  var teams = mongoose.model('teams')
  teams.findOne({ '_id': req.body.team_id }, (err, team) => {
    if (err) throw err
    var project = {
      'title': req.body.project_name
    }
    team.projects.push(project)
    team.save((err, updatedTeam) => {
      if (err) throw err
      res.json({ success: 'true', team: updatedTeam })
    })
  })
})

router.post('/projects', (req, res) => {
  var teams = mongoose.model('teams')
  teams.findOne({ '_id': req.body.team_id }, (err, team) => {
    if (err) throw err
    res.json({ success: 'true', projects: team.projects })
  })
})

router.post('/projects/all', (req, res) => {
  var teams = mongoose.model('teams')
  var users = mongoose.model('users')
  users.findOne({ 'email_id': req.body.email_id }, (err, user) => {
    if (err) throw err
    let userTeams = user.teams.map(a => a.team_id)
    teams.find({ '_id': { $in: userTeams } }, (err, team) => {
      let allProjects = team.map(a => a.projects.map(b => { return { title: b.title, date_of_creation: b.date_of_creation, _id: b._id } }))
      var allProjectsMerged = [].concat.apply([], allProjects)
      res.json({ success: 'true', projects: allProjectsMerged })
    })
  })
})

router.post('/project/board', (req, res) => {
  var teams = mongoose.model('teams')
  var users = mongoose.model('users')
  teams.findOne({ 'projects._id': req.body.project_id }, (err, team) => {
    var board = team.projects.filter(a => {
      if (a._id == req.body.project_id) {
        return a
      }
    })
    res.json({ success: 'true', board: board })
  })
})

module.exports = router
