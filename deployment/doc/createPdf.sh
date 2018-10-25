#!/usr/bin/env bash
asciidoctor-pdf -a pdf-stylesdir=resources/themes -a pdf-style=basic *.adoc
