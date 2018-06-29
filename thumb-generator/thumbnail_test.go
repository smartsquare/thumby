package main;

import (
    . "github.com/onsi/ginkgo"
    . "github.com/onsi/gomega"
    "testing"
)

func TestThumbnails(t *testing.T) {
    RegisterFailHandler(Fail)
    RunSpecs(t, "Thumbnail Service Suite")
}


var _ = Describe("", func() {

})