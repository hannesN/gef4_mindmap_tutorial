if [ ! -d "bin" ]; then
mkdir bin
fi

pandoc src/*.mmd -f markdown -o bin/minmap_tutorial.pdf