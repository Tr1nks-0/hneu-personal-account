'use strict';

var gulp = require('gulp');
var less = require('gulp-less');
var minify = require('gulp-minify-css');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');

gulp.task('less', function () {
    return gulp.src('./src/main/resources/theme/less/**/*.less')
        .pipe(less())
        .pipe(minify())
        .pipe(concat('custom.min.css'))
        .pipe(gulp.dest('./src/main/resources/theme/css/custom'));
});

gulp.task('js', function () {
    return gulp.src('./src/main/resources/theme/js/custom/**/*.js')
        .pipe(concat('custom.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./src/main/resources/theme/js'));
});

gulp.task('build', ['less', 'js']);