'use strict'

var Koa = require('koa')
var app = new Koa()
var fs = require('fs')
var heredoc = require('heredoc')
var ejs = require('ejs')

var site = "http://khrystal.tunnel.2bdata.com"

var createTimestamp = function() {
  return parseInt(new Date().getTime() / 1000, 10) + ''
}

var tpl = heredoc(function() {/*
<?xml version="1.0" encoding="UTF-8"?>
<!-- use in server-->
<UpdateResult>
    <!-- boolean -->
    <NewVersion value="<%= hasNewVersion %>"/>
    <!-- file url -->
    <FileUrl value="<%= fileUrl%>"/>
    <!-- patch url -->
    <PatchUrl value="<%= patchUrl %>"/>
    <!-- server apk version code -->
    <VersionCode value="<%= versionCode %>"/>
    <!-- server apk version name -->
    <VersionName value="<%= versionName %>"/>
    <!-- image -->
    <UpdateImage value="<%= updateImage %>"/>
    <!-- description -->
    <UpdateDespriation value="<%= updateDescription%>"/>
    <!-- apk md5 -->
    <MD5 value="<%= md5 %>"/>
    <!-- server site -->
    <Site value="<%= siteName %>"/>
</UpdateResult>
*/})

function getResult(versionCode) {
  var timestamp = createTimestamp()
  var hasNewVersion = (versionCode === 2)
  var fileUrl = site + '/downloadApk'
  var patchUrl = site + '/downloadPatch'
  var versionCode = 2
  var versionName = "2.0"
  var updateImage = ""
  var updateDescription = "2.0版本更新测试"
  var md5 = ""
  var siteName = site

  return {
    hasNewVersion: hasNewVersion,
    fileUrl: fileUrl,
    patchUrl: patchUrl,
    versionCode: versionCode,
    versionName: versionName,
    updateImage: updateImage,
    updateDescription: updateDescription,
    md5: md5,
    siteName: siteName
  }
}

app.use(function *(next) {
  var that = this
  if (this.url.indexOf('/checkAndroidUpdateXML') > -1) {
    var params = getResult(this.query.versionCode)
    this.body = ejs.render(tpl, params)
    return next
  }
  else if (this.url.indexOf('/checkAndroidUpdateJSON') > -1) {
    this.body = getResult(this.query.versionCode)
    return next
  }
  else if (this.url.indexOf('/downloadPatch') > -1) {
    var source = fs.readFileSync('')
    this.body = source
    return next
  }
  else if (this.url.indexOf('/downloadApk') > -1) {
    var source = fs.readFileSync('')
    this.body = source
    return next
  }
  yield next
})

app.listen(1234)
console.log('Listening :1234')
