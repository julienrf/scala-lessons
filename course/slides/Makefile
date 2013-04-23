PUBLISH_TO=~/sites/julienrf.github.com/scala-lessons/course

all: course.md Makefile
	pandoc -t slidy -s course.md -o course.html --standalone --smart --slide-level=2 --number-sections

class-hierarchy: ScalaClassHierarchy.png
	cp ScalaClassHierarchy.png ${PUBLISH_TO}

publish: all class-hierarchy
	cp course.html ${PUBLISH_TO}
