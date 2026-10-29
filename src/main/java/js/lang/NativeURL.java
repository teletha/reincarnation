/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The JavaScript {@code URL}.
 */
@Native
public final class NativeURL {

    /** The URL. */
    private URL url;

    /**
     * Create the URL.
     * 
     * @param url A URL text.
     */
    @Native("new URL($0)")
    public NativeURL(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + url, e);
        }
    }

    /**
     * Create the URL.
     * 
     * @param url A URL text.
     * @param base A base URL.
     */
    @Native("new URL($0, $1)")
    public NativeURL(String url, String base) {
        try {
            this.url = new URL(new URL(base), url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + url, e);
        }
    }

    /**
     * Get the href.
     * 
     * @return A href.
     */
    @Native("$this.href")
    public String href() {
        return url.toString();
    }

    /**
     * Set the href.
     * 
     * @param href A href.
     */
    @Native("$this.href = $0")
    public void href(String href) {
        try {
            this.url = new URL(href);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + href, e);
        }
    }

    /**
     * Get the protocol.
     * 
     * @return A protocol.
     */
    @Native("$this.protocol")
    public String protocol() {
        return url.getProtocol() + ":";
    }

    /**
     * Set the protocol.
     * 
     * @param protocol A protocol.
     */
    @Native("$this.protocol = $0")
    public void protocol(String protocol) {
        String value = protocol.endsWith(":") ? protocol.substring(0, protocol.length() - 1) : protocol;
        update(value, url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
    }

    /**
     * Get the host.
     * 
     * @return A host.
     */
    @Native("$this.host")
    public String host() {
        String port = port();
        return port.isEmpty() ? url.getHost() : url.getHost() + ":" + port;
    }

    /**
     * Set the host.
     * 
     * @param host A host.
     */
    @Native("$this.host = $0")
    public void host(String host) {
        int index = host.lastIndexOf(':');
        String name = host;
        int port = -1;
        if (index > 0 && index + 1 < host.length()) {
            try {
                port = Integer.parseInt(host.substring(index + 1));
                name = host.substring(0, index);
            } catch (NumberFormatException e) {
                // keep the whole host
            }
        }
        update(url.getProtocol(), url.getUserInfo(), name, port, url.getPath(), url.getQuery(), url.getRef());
    }

    /**
     * Get the hostname.
     * 
     * @return A hostname.
     */
    @Native("$this.hostname")
    public String hostname() {
        return url.getHost();
    }

    /**
     * Set the hostname.
     * 
     * @param hostname A hostname.
     */
    @Native("$this.hostname = $0")
    public void hostname(String hostname) {
        update(url.getProtocol(), url.getUserInfo(), hostname, url.getPort(), url.getPath(), url.getQuery(), url.getRef());
    }

    /**
     * Get the port.
     * 
     * @return A port.
     */
    @Native("$this.port")
    public String port() {
        int port = url.getPort();
        if (port < 0 || port == url.getDefaultPort()) {
            return "";
        }
        return String.valueOf(port);
    }

    /**
     * Set the port.
     * 
     * @param port A port.
     */
    @Native("$this.port = $0")
    public void port(String port) {
        int value = port == null || port.isEmpty() ? -1 : Integer.parseInt(port);
        update(url.getProtocol(), url.getUserInfo(), url.getHost(), value, url.getPath(), url.getQuery(), url.getRef());
    }

    /**
     * Get the pathname.
     * 
     * @return A pathname.
     */
    @Native("$this.pathname")
    public String pathname() {
        String path = url.getPath();
        return path == null || path.isEmpty() ? "/" : path;
    }

    /**
     * Set the pathname.
     * 
     * @param pathname A pathname.
     */
    @Native("$this.pathname = $0")
    public void pathname(String pathname) {
        update(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), pathname, url.getQuery(), url.getRef());
    }

    /**
     * Get the search.
     * 
     * @return A search.
     */
    @Native("$this.search")
    public String search() {
        String query = url.getQuery();
        return query == null || query.isEmpty() ? "" : "?" + query;
    }

    /**
     * Set the search.
     * 
     * @param search A search.
     */
    @Native("$this.search = $0")
    public void search(String search) {
        String query = search == null || search.isEmpty() ? null : (search.charAt(0) == '?' ? search.substring(1) : search);
        update(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), query, url.getRef());
    }

    /**
     * Get the hash.
     * 
     * @return A hash.
     */
    @Native("$this.hash")
    public String hash() {
        String ref = url.getRef();
        return ref == null || ref.isEmpty() ? "" : "#" + ref;
    }

    /**
     * Set the hash.
     * 
     * @param hash A hash.
     */
    @Native("$this.hash = $0")
    public void hash(String hash) {
        String ref = hash == null || hash.isEmpty() ? null : (hash.charAt(0) == '#' ? hash.substring(1) : hash);
        update(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), ref);
    }

    /**
     * Get the username.
     * 
     * @return A username.
     */
    @Native("$this.username")
    public String username() {
        String userInfo = url.getUserInfo();
        if (userInfo == null) {
            return "";
        }
        int index = userInfo.indexOf(':');
        return index < 0 ? userInfo : userInfo.substring(0, index);
    }

    /**
     * Get the password.
     * 
     * @return A password.
     */
    @Native("$this.password")
    public String password() {
        String userInfo = url.getUserInfo();
        if (userInfo == null) {
            return "";
        }
        int index = userInfo.indexOf(':');
        return index < 0 ? "" : userInfo.substring(index + 1);
    }

    /**
     * Get the origin.
     * 
     * @return An origin.
     */
    @Native("$this.origin")
    public String origin() {
        String protocol = url.getProtocol();
        if (protocol.equals("file")) {
            return "null";
        }
        return protocol + "://" + host();
    }

    /**
     * Get the search parameters.
     * 
     * @return Search parameters.
     */
    @Native("$this.searchParams")
    public NativeURLSearchParams searchParams() {
        return new NativeURLSearchParams(url.getQuery());
    }

    /**
     * Get the JSON value.
     * 
     * @return A href.
     */
    @Native("$this.toJSON()")
    public String toJSON() {
        return href();
    }

    /**
     * Update the URL.
     * 
     * @param protocol A protocol.
     * @param userInfo A user info.
     * @param host A host.
     * @param port A port.
     * @param path A path.
     * @param query A query.
     * @param fragment A fragment.
     */
    private void update(String protocol, String userInfo, String host, int port, String path, String query, String fragment) {
        try {
            this.url = new URI(protocol, userInfo, host, port, path, query, fragment).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }

    /**
     * Create the object URL.
     * 
     * @param blob A blob.
     * @return An object URL.
     */
    @Native("URL.createObjectURL($0)")
    public static String createObjectURL(Object blob) {
        return "blob:mock";
    }

    /**
     * Revoke the object URL.
     * 
     * @param url An object URL.
     */
    @Native("URL.revokeObjectURL($0)")
    public static void revokeObjectURL(String url) {
    }

    /**
     * Check whether the URL can be parsed.
     * 
     * @param url A URL.
     * @param base A base URL.
     * @return A result.
     */
    @Native("URL.canParse($0, $1)")
    public static boolean canParse(String url, String base) {
        try {
            new URL(new URL(base), url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return href();
    }
}
