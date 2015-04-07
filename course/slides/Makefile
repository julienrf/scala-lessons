PUBLISH_TO=~/sites/julienrf.github.com/scala-lessons/course
IMAGES=$(wildcard *.png)
SOURCES=intro.md references.md core.md sbt.md adt.md std.md sugar.md modules.md imperative.md polymorphism.md

all: $(SOURCES) Makefile
	pandoc -t slidy -s -o index.html --standalone --smart --slide-level=2 --number-sections $(SOURCES)

images: $(IMAGES)
	cp $(IMAGES) $(PUBLISH_TO)

publish: all images
	cp index.html $(PUBLISH_TO)

pdf: $(SOURCES)
	pandoc -o scala-course.pdf $(SOURCES)