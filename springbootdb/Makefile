.PHONY: all run clean prepare

DATA_DIR = data

all: prepare run

prepare:
	@mkdir -p $(DATA_DIR)

run: prepare
	mvn spring-boot:run

clean:
	rm -rf $(DATA_DIR)
	mvn clean
