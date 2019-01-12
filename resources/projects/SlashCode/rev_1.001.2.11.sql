# MySQL dump 7.1
#
# Host: localhost    Database: slash
#--------------------------------------------------------
# Server version	3.22.32

#
# Table structure for table 'abusers'
#
DROP TABLE IF EXISTS abusers;
CREATE TABLE abusers (
  abuser_id int(5) DEFAULT '0' NOT NULL auto_increment,
  host_name varchar(25) DEFAULT '' NOT NULL,
  pagename varchar(20) DEFAULT '' NOT NULL,
  ts datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  reason varchar(120) DEFAULT '' NOT NULL,
  querystring varchar(60) DEFAULT '' NOT NULL,
  PRIMARY KEY (abuser_id),
  KEY host_name (host_name),
  KEY reason (reason)
);

#
# Table structure for table 'accesslog'
#
DROP TABLE IF EXISTS accesslog;
CREATE TABLE accesslog (
  id int(5) DEFAULT '0' NOT NULL auto_increment,
  host_addr varchar(16) DEFAULT '' NOT NULL,
  op varchar(8),
  dat varchar(32),
  uid int(1) DEFAULT '0' NOT NULL,
  ts datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  query_string varchar(50),
  user_agent varchar(50),
  PRIMARY KEY (id)
);

#
# Table structure for table 'authors'
#
DROP TABLE IF EXISTS authors;
CREATE TABLE authors (
  aid char(30) DEFAULT '' NOT NULL,
  name char(50),
  url char(50),
  email char(50),
  quote char(50),
  copy char(255),
  pwd char(8),
  seclev int(11),
  lasttitle char(20),
  section char(20),
  deletedsubmissions int(11) DEFAULT '0',
  matchname char(30),
  PRIMARY KEY (aid)
);

#
# Table structure for table 'blocks'
#
DROP TABLE IF EXISTS blocks;
CREATE TABLE blocks (
  bid varchar(30) DEFAULT '' NOT NULL,
  block text,
  aid varchar(20),
  seclev int(1),
  type varchar(20) DEFAULT '' NOT NULL,
  description text,
  blockbak text,
  section varchar(30) DEFAULT '' NOT NULL,
  ordernum tinyint(4) DEFAULT '0',
  title varchar(128),
  portal tinyint(4) DEFAULT '0',
  url varchar(128),
  rdf varchar(255),
  retrieve int(1) DEFAULT '0',
  PRIMARY KEY (bid),
  KEY type (type),
  KEY section (section)
);

#
# Table structure for table 'commentcodes'
#
DROP TABLE IF EXISTS commentcodes;
CREATE TABLE commentcodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'commentmodes'
#
DROP TABLE IF EXISTS commentmodes;
CREATE TABLE commentmodes (
  mode varchar(16) DEFAULT '' NOT NULL,
  name varchar(32),
  description varchar(64),
  PRIMARY KEY (mode)
);

#
# Table structure for table 'comments'
#
DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
  sid varchar(30) DEFAULT '' NOT NULL,
  cid int(15) DEFAULT '0' NOT NULL,
  pid int(15) DEFAULT '0' NOT NULL,
  date datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  host_name varchar(30) DEFAULT '0.0.0.0' NOT NULL,
  subject varchar(50) DEFAULT '' NOT NULL,
  comment text NOT NULL,
  uid int(1) DEFAULT '0' NOT NULL,
  points int(1) DEFAULT '0' NOT NULL,
  lastmod int(1),
  reason int(11) DEFAULT '0',
  PRIMARY KEY (sid,cid),
  KEY display (sid,points,uid),
  KEY byname (uid,points),
  KEY theusual (sid,uid,points,cid),
  KEY countreplies (sid,pid)
);

#
# Table structure for table 'content_filters'
#
DROP TABLE IF EXISTS content_filters;
CREATE TABLE content_filters (
  filter_id int(4) DEFAULT '0' NOT NULL auto_increment,
  regex varchar(100) DEFAULT '' NOT NULL,
  modifier varchar(5) DEFAULT '' NOT NULL,
  field varchar(20) DEFAULT '' NOT NULL,
  ratio float(6,4) DEFAULT '0.0000' NOT NULL,
  minimum_match int(6) DEFAULT '0' NOT NULL,
  minimum_length int(10) DEFAULT '0' NOT NULL,
  err_message varchar(150) DEFAULT '',
  maximum_length int(10) DEFAULT '0' NOT NULL,
  PRIMARY KEY (filter_id),
  KEY regex (regex),
  KEY field (field)
);

#
# Table structure for table 'dateformats'
#
DROP TABLE IF EXISTS dateformats;
CREATE TABLE dateformats (
  id int(1) DEFAULT '0' NOT NULL,
  format varchar(32),
  description varchar(64),
  PRIMARY KEY (id)
);

#
# Table structure for table 'discussions'
#
DROP TABLE IF EXISTS discussions;
CREATE TABLE discussions (
  sid varchar(20) DEFAULT '' NOT NULL,
  title varchar(128),
  url varchar(128),
  ts datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  PRIMARY KEY (sid)
);

#
# Table structure for table 'displaycodes'
#
DROP TABLE IF EXISTS displaycodes;
CREATE TABLE displaycodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'formkeys'
#
DROP TABLE IF EXISTS formkeys;
CREATE TABLE formkeys (
  formkey varchar(20) DEFAULT '' NOT NULL,
  formname varchar(20) DEFAULT '' NOT NULL,
  id varchar(30) DEFAULT '' NOT NULL,
  sid varchar(30) DEFAULT '' NOT NULL,
  uid int(11) DEFAULT '0' NOT NULL,
  host_name varchar(30) DEFAULT '0.0.0.0' NOT NULL,
  value int(1) DEFAULT '0' NOT NULL,
  cid int(15) DEFAULT '0' NOT NULL,
  ts int(12) DEFAULT '0' NOT NULL,
  submit_ts int(12) DEFAULT '0' NOT NULL,
  content_length int(4) DEFAULT '0' NOT NULL,
  PRIMARY KEY (formkey),
  KEY formname (formname),
  KEY id (id),
  KEY ts (ts),
  KEY submit_ts (submit_ts)
);

#
# Table structure for table 'hitters'
#
DROP TABLE IF EXISTS hitters;
CREATE TABLE hitters (
  id int(5) DEFAULT '0' NOT NULL auto_increment,
  host_addr varchar(15) DEFAULT '' NOT NULL,
  hits int(6) DEFAULT '0' NOT NULL,
  PRIMARY KEY (id),
  KEY host_addr (host_addr),
  KEY hits (hits)
);

#
# Table structure for table 'isolatemodes'
#
DROP TABLE IF EXISTS isolatemodes;
CREATE TABLE isolatemodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'issuemodes'
#
DROP TABLE IF EXISTS issuemodes;
CREATE TABLE issuemodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'maillist'
#
DROP TABLE IF EXISTS maillist;
CREATE TABLE maillist (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'menus'
#
DROP TABLE IF EXISTS menus;
CREATE TABLE menus (
  id int(5) DEFAULT '0' NOT NULL auto_increment,
  menu varchar(20) DEFAULT '' NOT NULL,
  label varchar(200) DEFAULT '' NOT NULL,
  value text,
  seclev int(1),
  menuorder int(5),
  PRIMARY KEY (id),
  KEY page_labels (menu,label),
  UNIQUE page_labels_un (menu,label)
);

#
# Table structure for table 'metamodlog'
#
DROP TABLE IF EXISTS metamodlog;
CREATE TABLE metamodlog (
  mmid int(11) DEFAULT '0' NOT NULL,
  uid int(11) DEFAULT '0' NOT NULL,
  val int(11) DEFAULT '0' NOT NULL,
  ts datetime,
  id int(11) DEFAULT '0' NOT NULL auto_increment,
  flag int(11) NOT NULL,
  PRIMARY KEY (id)
);

#
# Table structure for table 'moderatorlog'
#
DROP TABLE IF EXISTS moderatorlog;
CREATE TABLE moderatorlog (
  id int(1) DEFAULT '0' NOT NULL auto_increment,
  uid int(1) DEFAULT '0' NOT NULL,
  val int(1) DEFAULT '0' NOT NULL,
  sid varchar(30) DEFAULT '' NOT NULL,
  ts datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  cid int(1) DEFAULT '0' NOT NULL,
  reason int(11) DEFAULT '0',
  active int(1) DEFAULT '1' NOT NULL,
  PRIMARY KEY (id),
  KEY sid (sid,cid),
  KEY sid_2 (sid,uid,cid)
);

#
# Table structure for table 'newstories'
#
DROP TABLE IF EXISTS newstories;
CREATE TABLE newstories (
  sid varchar(20) DEFAULT '' NOT NULL,
  tid varchar(20) DEFAULT '' NOT NULL,
  aid varchar(30) DEFAULT '' NOT NULL,
  commentcount int(1) DEFAULT '0',
  title varchar(100) DEFAULT '' NOT NULL,
  dept varchar(100),
  time datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  introtext text,
  bodytext text,
  writestatus int(1) DEFAULT '0' NOT NULL,
  hits int(1) DEFAULT '0' NOT NULL,
  section varchar(15) DEFAULT '' NOT NULL,
  displaystatus int(1) DEFAULT '0' NOT NULL,
  commentstatus int(1),
  hitparade varchar(64) DEFAULT '0,0,0,0,0,0,0',
  relatedtext text,
  extratext text,
  PRIMARY KEY (sid),
  KEY time (time),
  KEY searchform (displaystatus,time)
);

#
# Table structure for table 'pollanswers'
#
DROP TABLE IF EXISTS pollanswers;
CREATE TABLE pollanswers (
  qid char(20) DEFAULT '' NOT NULL,
  aid int(11) DEFAULT '0' NOT NULL,
  answer char(255),
  votes int(11),
  PRIMARY KEY (qid,aid)
);

#
# Table structure for table 'pollquestions'
#
DROP TABLE IF EXISTS pollquestions;
CREATE TABLE pollquestions (
  qid char(20) DEFAULT '' NOT NULL,
  question char(255) DEFAULT '' NOT NULL,
  voters int(11),
  date datetime,
  PRIMARY KEY (qid)
);

#
# Table structure for table 'pollvoters'
#
DROP TABLE IF EXISTS pollvoters;
CREATE TABLE pollvoters (
  qid char(20) DEFAULT '' NOT NULL,
  id char(35) DEFAULT '' NOT NULL,
  time datetime,
  uid int(11) DEFAULT '0' NOT NULL,
  KEY qid (qid,id,uid)
);

#
# Table structure for table 'postmodes'
#
DROP TABLE IF EXISTS postmodes;
CREATE TABLE postmodes (
  code char(10) DEFAULT '' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'sections'
#
DROP TABLE IF EXISTS sections;
CREATE TABLE sections (
  section varchar(30) DEFAULT '' NOT NULL,
  artcount int(11),
  title varchar(64),
  qid varchar(20) DEFAULT '' NOT NULL,
  isolate int(1),
  issue int(1),
  extras int(11) DEFAULT '0',
  PRIMARY KEY (section)
);

#
# Table structure for table 'sessions'
#
DROP TABLE IF EXISTS sessions;
CREATE TABLE sessions (
  session varchar(20) DEFAULT '' NOT NULL,
  aid varchar(30),
  logintime datetime,
  lasttime datetime,
  lasttitle varchar(50),
  PRIMARY KEY (session)
);

#
# Table structure for table 'sortcodes'
#
DROP TABLE IF EXISTS sortcodes;
CREATE TABLE sortcodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'statuscodes'
#
DROP TABLE IF EXISTS statuscodes;
CREATE TABLE statuscodes (
  code int(1) DEFAULT '0' NOT NULL,
  name char(32),
  PRIMARY KEY (code)
);

#
# Table structure for table 'stories'
#
DROP TABLE IF EXISTS stories;
CREATE TABLE stories (
  sid varchar(20) DEFAULT '' NOT NULL,
  tid varchar(20) DEFAULT '' NOT NULL,
  aid varchar(30) DEFAULT '' NOT NULL,
  commentcount int(1) DEFAULT '0',
  title varchar(100) DEFAULT '' NOT NULL,
  dept varchar(100),
  time datetime DEFAULT '0000-00-00 00:00:00' NOT NULL,
  introtext text,
  bodytext text,
  writestatus int(1) DEFAULT '0' NOT NULL,
  hits int(1) DEFAULT '0' NOT NULL,
  section varchar(15) DEFAULT '' NOT NULL,
  displaystatus int(1) DEFAULT '0' NOT NULL,
  commentstatus int(1),
  hitparade varchar(64) DEFAULT '0,0,0,0,0,0,0',
  relatedtext text,
  extratext text,
  PRIMARY KEY (sid),
  KEY time (time),
  KEY searchform (displaystatus,time)
);

#
# Table structure for table 'storiestuff'
#
DROP TABLE IF EXISTS storiestuff;
CREATE TABLE storiestuff (
  sid varchar(20) DEFAULT '' NOT NULL,
  hits int(1) DEFAULT '0' NOT NULL,
  PRIMARY KEY (sid)
);

#
# Table structure for table 'submissions'
#
DROP TABLE IF EXISTS submissions;
CREATE TABLE submissions (
  subid varchar(15) DEFAULT '' NOT NULL,
  email varchar(50),
  name varchar(50),
  time datetime,
  subj varchar(50),
  story text,
  tid varchar(20),
  note varchar(30),
  section varchar(30) DEFAULT '' NOT NULL,
  comment varchar(255),
  uid int(11) DEFAULT '0' NOT NULL,
  del tinyint(4) DEFAULT '0' NOT NULL,
  PRIMARY KEY (subid),
  KEY subid (subid,section)
);

#
# Table structure for table 'threshcodes'
#
DROP TABLE IF EXISTS threshcodes;
CREATE TABLE threshcodes (
  thresh int(1) DEFAULT '0' NOT NULL,
  description char(64),
  PRIMARY KEY (thresh)
);

#
# Table structure for table 'topics'
#
DROP TABLE IF EXISTS topics;
CREATE TABLE topics (
  tid char(20) DEFAULT '' NOT NULL,
  image char(30),
  alttext char(40),
  width int(11),
  height int(11),
  PRIMARY KEY (tid)
);

#
# Table structure for table 'tzcodes'
#
DROP TABLE IF EXISTS tzcodes;
CREATE TABLE tzcodes (
  tz char(3) DEFAULT '' NOT NULL,
  offset int(1),
  description varchar(64),
  PRIMARY KEY (tz)
);

#
# Table structure for table 'users'
#
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  uid int(11) DEFAULT '0' NOT NULL auto_increment,
  nickname varchar(20) DEFAULT '' NOT NULL,
  realemail varchar(50) DEFAULT '' NOT NULL,
  fakeemail varchar(50),
  homepage varchar(100),
  passwd char(32) DEFAULT '' NOT NULL,
  sig varchar(160),
  seclev int(11) DEFAULT '0' NOT NULL,
  matchname varchar(20),
  newpasswd char(8),
  PRIMARY KEY (uid),
  KEY login (uid,passwd,nickname),
  KEY chk4user (nickname,realemail),
  KEY chk4email (realemail)
);

#
# Table structure for table 'users_comments'
#
DROP TABLE IF EXISTS users_comments;
CREATE TABLE users_comments (
  uid int(11) DEFAULT '0' NOT NULL,
  points int(11) DEFAULT '0' NOT NULL,
  posttype varchar(10) DEFAULT 'html' NOT NULL,
  defaultpoints int(11) DEFAULT '1' NOT NULL,
  highlightthresh int(11) DEFAULT '4' NOT NULL,
  maxcommentsize int(11) DEFAULT '4096' NOT NULL,
  hardthresh tinyint(4) DEFAULT '0' NOT NULL,
  clbig int(11) DEFAULT '0' NOT NULL,
  clsmall int(11) DEFAULT '0' NOT NULL,
  reparent tinyint(4) DEFAULT '1' NOT NULL,
  nosigs tinyint(4) DEFAULT '0' NOT NULL,
  commentlimit int(11) DEFAULT '100' NOT NULL,
  commentspill int(11) DEFAULT '50' NOT NULL,
  commentsort int(1) DEFAULT '0',
  noscores tinyint(4) DEFAULT '0' NOT NULL,
  mode varchar(10) DEFAULT 'thread',
  threshold int(1) DEFAULT '0',
  PRIMARY KEY (uid)
);

#
# Table structure for table 'users_index'
#
DROP TABLE IF EXISTS users_index;
CREATE TABLE users_index (
  uid int(11) DEFAULT '0' NOT NULL,
  extid varchar(255),
  exaid varchar(100),
  exsect varchar(100),
  exboxes varchar(255),
  maxstories int(11) DEFAULT '30' NOT NULL,
  noboxes tinyint(4) DEFAULT '0' NOT NULL,
  PRIMARY KEY (uid)
);

#
# Table structure for table 'users_info'
#
DROP TABLE IF EXISTS users_info;
CREATE TABLE users_info (
  uid int(11) DEFAULT '0' NOT NULL,
  totalmods int(11) DEFAULT '0' NOT NULL,
  realname varchar(50),
  bio text,
  tokens int(11) DEFAULT '0' NOT NULL,
  lastgranted date DEFAULT '0000-00-00' NOT NULL,
  karma int(11) DEFAULT '0' NOT NULL,
  maillist tinyint(4) DEFAULT '0' NOT NULL,
  totalcomments int(1) DEFAULT '0',
  lastmm date DEFAULT '0000-00-00' NOT NULL,
  lastaccess date DEFAULT '0000-00-00' NOT NULL,
  lastmmid int(11) DEFAULT '0' NOT NULL,
  m2fair int(11) DEFAULT '0' NOT NULL,
  m2unfair int(11) DEFAULT '0' NOT NULL,
  m2fairvotes int(11) DEFAULT '0' NOT NULL,
  m2unfairvotes int(11) DEFAULT '0' NOT NULL,
  PRIMARY KEY (uid)
);

#
# Table structure for table 'users_key'
#
DROP TABLE IF EXISTS users_key;
CREATE TABLE users_key (
  uid int(11) DEFAULT '0' NOT NULL,
  pubkey text,
  PRIMARY KEY (uid)
);

#
# Table structure for table 'users_prefs'
#
DROP TABLE IF EXISTS users_prefs;
CREATE TABLE users_prefs (
  uid int(11) DEFAULT '0' NOT NULL,
  willing tinyint(4) DEFAULT '1' NOT NULL,
  dfid int(11) DEFAULT '0' NOT NULL,
  tzcode char(3) DEFAULT 'edt' NOT NULL,
  noicons tinyint(4) DEFAULT '0' NOT NULL,
  light tinyint(4) DEFAULT '0' NOT NULL,
  mylinks varchar(255) DEFAULT '' NOT NULL,
  PRIMARY KEY (uid)
);

#
# Table structure for table 'vars'
#
DROP TABLE IF EXISTS vars;
CREATE TABLE vars (
  name varchar(32) DEFAULT '' NOT NULL,
  value text,
  description varchar(127),
  datatype varchar(10),
  dataop varchar(12),
  PRIMARY KEY (name)
);

