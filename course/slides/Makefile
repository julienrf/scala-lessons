all: course.md Makefile
	pandoc -t slidy -s course.md -o course.html --standalone --smart --slide-level=2 --number-sections

publish: all
	cp course.html ~/sites/julienrf.github.com/scala-lessons/course
