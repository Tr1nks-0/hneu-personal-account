'use strict';

var gulp = require('gulp');
var less = require('gulp-less');
var minify = require('gulp-minify-css');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var merge = require('merge-stream');
var del = require('del');

var cssSrcLocaltion = './src/main/resources/theme/css';
var jsSrcLocaltion = './src/main/resources/theme/js';

gulp.task('clean', function(){
    return del([
        './src/main/resources/theme/css/main.css',
        './src/main/resources/theme/css/main.min.css',
        './src/main/resources/theme/js/main.js',
        './src/main/resources/theme/js/main.min.js'
    ]);
});

gulp.task('css', function () {
    return merge(lessStream(), cssStream())
        .pipe(concat('main.css'))
        .pipe(gulp.dest(cssSrcLocaltion));
});

gulp.task('cssMinify', function () {
    return merge(lessStream(), cssStream())
        .pipe(concat('main.min.css'))
        .pipe(minify())
        .pipe(gulp.dest(cssSrcLocaltion));
});

function lessStream() {
   return gulp.src('./src/main/resources/theme/less/styles.less')
        .pipe(less())
}

function cssStream() {
    return gulp.src('./src/main/resources/theme/css/template/*.css')
        .pipe(concat('css-files.css'));
}

gulp.task('js', function () {
    return jsStream()
        .pipe(concat('main.js'))
        .pipe(gulp.dest(jsSrcLocaltion));
});

gulp.task('jsMinify', function () {
    return jsStream()
        .pipe(concat('main.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(jsSrcLocaltion));
});

function jsStream() {
    return gulp.src('./src/main/resources/theme/js/custom/**/*.js')
        .pipe(concat('js-files.js'));
}

gulp.task('build', ['clean', 'css', 'js', 'cssMinify', 'jsMinify']);