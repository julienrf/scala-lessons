PUBLISH_TO=~/sites/julienrf.github.com/scala-lessons/course
IMAGES=$(wildcard *.png)

all: *.md Makefile
	pandoc -t slidy -s -o index.html --standalone --smart --slide-level=2 --number-sections intro.md core.md sbt.md adt.md std.md sugar.md modules.md imperative.md polymorphism.md references.md

images: $(IMAGES)
	cp $(IMAGES) $(PUBLISH_TO)

publish: all images
	cp index.html $(PUBLISH_TO)
