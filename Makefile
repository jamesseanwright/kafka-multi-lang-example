ifndef DOCKER_HOST
	DOCKER_HOST := unix:///var/run/docker.sock
endif

build:
	@# Annoyingly, the bootBuildImage Gradle task
	@# doesn't work when running Docker in
	@# rootless mode as it doesn't pass the host's
	@# DOCKER_HOST environment variable to the
	@# builder, so we make use of the pack CLI
	@# to build the image directly.

	pack build kafka-example-java --path java \
		--docker-host ${DOCKER_HOST} \
		--builder paketobuildpacks/builder-jammy-full \
		--buildpack gcr.io/paketo-buildpacks/java \
		--buildpack gcr.io/paketo-buildpacks/spring-boot:5
