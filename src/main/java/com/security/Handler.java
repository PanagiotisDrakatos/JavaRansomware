package com.security;

interface Handler<I, O> {
    O process(I input);
}
