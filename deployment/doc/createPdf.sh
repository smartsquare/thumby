#!/usr/bin/env bash

if which asciidoctor-pdf >& /dev/null
then
    ASCIIDOCTOR=(asciidoctor-pdf)
else
    ASCIIDOCTOR=(docker run --rm -v "$(pwd):/documents" asciidoctor/docker-asciidoctor asciidoctor-pdf)
fi

set -x
${ASCIIDOCTOR[@]} -a pdf-stylesdir=resources/themes -a pdf-style=basic *.adoc
