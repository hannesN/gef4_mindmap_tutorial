if [ ! -d "bin" ]; then
mkdir bin
fi

pandoc src/*.md -f markdown -o bin/minmap_tutorial.pdf
