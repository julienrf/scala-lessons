PUBLISH_TO=~/sites/julienrf.github.com/scala-lessons/course
IMAGES=$(wildcard *.png)

all: course.md Makefile
	pandoc -t slidy -s course.md -o index.html --standalone --smart --slide-level=2 --number-sections

images: $(IMAGES)
	cp $(IMAGES) $(PUBLISH_TO)

publish: all images
	cp index.html $(PUBLISH_TO)
