## Travis builds now enabled

Builds are now verified by Travis (see [issue #20](https://github.com/java-json-tools/json-patch/issues/20) from the json-patch project for details)

https://travis-ci.org/java-json-tools/json-schema-validator

## Read me first

The **current** version of this project is licensed under both LGPLv3 (or later) and ASL 2.0. The old version
(2.0.x) was licensed under LGPL 3.0 (or later) only.

**Version 2.2 is out**. See [here](https://github.com/java-json-tools/json-schema-validator/wiki/Whatsnew_22)
for the list of changes compared to 2.0. And of course, it still has [all the
features](https://github.com/daveclayton/json-schema-validator/wiki/Features) of older versions.

## What this is

This is an implementation with complete validation support for the latest JSON Schema draft (v4,
including hyperschema syntax support) and the previous draft (v3 -- no hyperschema support though).
Its list of features would be too long to enumerate here; please refer to the links above!

Should you wonder about it, this library is reported to [work on
Android](http://stackoverflow.com/questions/14511468/java-android-validate-string-json-against-string-schema).
Starting with version 2.2.x, all APK conflicts have been resolved, so you can use this in this
context as well.

## Google Group

This project has a dedicated [Google
group](https://groups.google.com/forum/?fromgroups#!forum/json-schema-validator). For any questions
you have about this software package, feel free to post! The author (me) will try and respond in a
timely manner.

## Testing online

You can [test this library online](http://json-schema-validator.herokuapp.com); this web site is in
a [project of its own](https://github.com/java-json-tools/json-schema-validator-demo), which you can fork and
run by yourself.

## Versions

* current stable version: **2.2.6**
  ([ChangeLog](https://github.com/java-json-tools/json-schema-validator/wiki/ChangeLog_22x),
  [Javadoc](http://java-json-tools.github.io/json-schema-validator/2.2.x/index.html), [code
  samples](http://java-json-tools.github.io/json-schema-validator/2.2.x/index.html?com/github/fge/jsonschema/examples/package-summary.html)).
* old stable version: **2.0.4**
  ([ChangeLog](https://github.com/java-json-tools/json-schema-validator/wiki/ChangeLog_20x),
  [Javadoc](http://java-json-tools.github.io/json-schema-validator/2.0.x/index.html), [code
  samples](http://java-json-tools.github.io/json-schema-validator/2.0.x/index.html?com/github/fge/jsonschema/examples/package-summary.html)).

## Available downloads

### Gradle/maven

This package is available on Maven central; the artifact is as follows:

Gradle:

```groovy
dependencies {
    compile(group: "com.github.fge", name: "json-schema-validator", version: "yourVersionHere");
}
```

Maven:

```xml
<dependency>
    <groupId>com.github.fge</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>your-version-here</version>
</dependency>
```

### "Full" jar; command line

This jar contains the library plus all its dependencies. Download the **lib** jar (a little more
than 6 MiB) from [Bintray](https://bintray.com/fge/maven/json-schema-validator/view).

## Versioning scheme policy

The versioning scheme is defined by the **middle digit** of the version number:

* if this number is **even**, then this is the **stable** version; no new features will be
  added to such versions, and the user API will not change (save for some additions if requested).
* if this number is **odd**, then this is the **development** version; new features will be
  added to those versions only, **and the user API may change**.

## Relevant documents

This implementation is based on the following drafts:

* [JSON Schema Internet draft, version 4](http://tools.ietf.org/html/draft-zyp-json-schema-04)
  ([link to validation spec](http://tools.ietf.org/html/draft-fge-json-schema-validation-00));
* [JSON Schema Internet draft, version 3](http://tools.ietf.org/html/draft-zyp-json-schema-03);
* [JSON Reference Internet draft, version
  3](http://tools.ietf.org/html/draft-pbryan-zyp-json-ref-03);
* [JSON Pointer (RFC 6901)](http://tools.ietf.org/html/rfc6901).

## More...

For a detailed discussion of the implementation, see
[here](https://github.com/java-json-tools/json-schema-validator/wiki/Status).

Please see the [wiki](https://github.com/java-json-tools/json-schema-validator/wiki/) for more
details.

