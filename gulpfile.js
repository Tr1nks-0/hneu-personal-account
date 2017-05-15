'use strict';

var gulp = require('gulp');
var less = require('gulp-less');
var minify = require('gulp-minify-css');
var concat = require('gulp-concat');

gulp.task('less', function () {
    return gulp.src('./src/main/resources/theme/less/**/*.less')
        .pipe(less())
        .pipe(minify())
        .pipe(concat('custom.min.css'))
        .pipe(gulp.dest('./src/main/resources/theme/css/custom'));
});

gulp.task('build', ['less']);