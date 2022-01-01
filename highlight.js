/*!
  Highlight.js v11.2.0 (git: 2a5c592e5e)
  (c) 2006-2021 Ivan Sagalaev and other contributors
  License: BSD-3-Clause
 */
var hljs=function(){"use strict";var e={exports:{}};function n(e){
return e instanceof Map?e.clear=e.delete=e.set=()=>{
throw Error("map is read-only")}:e instanceof Set&&(e.add=e.clear=e.delete=()=>{
throw Error("set is read-only")
}),Object.freeze(e),Object.getOwnPropertyNames(e).forEach((t=>{var a=e[t]
;"object"!=typeof a||Object.isFrozen(a)||n(a)})),e}
e.exports=n,e.exports.default=n;var t=e.exports;class a{constructor(e){
void 0===e.data&&(e.data={}),this.data=e.data,this.isMatchIgnored=!1}
ignoreMatch(){this.isMatchIgnored=!0}}function i(e){
return e.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/"/g,"&quot;").replace(/'/g,"&#x27;")
}function s(e,...n){const t=Object.create(null);for(const n in e)t[n]=e[n]
;return n.forEach((e=>{for(const n in e)t[n]=e[n]})),t}const r=e=>!!e.kind
;class o{constructor(e,n){
this.buffer="",this.classPrefix=n.classPrefix,e.walk(this)}addText(e){
this.buffer+=i(e)}openNode(e){if(!r(e))return;let n=e.kind
;n=e.sublanguage?"language-"+n:((e,{prefix:n})=>{if(e.includes(".")){
const t=e.split(".")
;return[`${n}${t.shift()}`,...t.map(((e,n)=>`${e}${"_".repeat(n+1)}`))].join(" ")
}return`${n}${e}`})(n,{prefix:this.classPrefix}),this.span(n)}closeNode(e){
r(e)&&(this.buffer+="</span>")}value(){return this.buffer}span(e){
this.buffer+=`<span class="${e}">`}}class l{constructor(){this.rootNode={
children:[]},this.stack=[this.rootNode]}get top(){
return this.stack[this.stack.length-1]}get root(){return this.rootNode}add(e){
this.top.children.push(e)}openNode(e){const n={kind:e,children:[]}
;this.add(n),this.stack.push(n)}closeNode(){
if(this.stack.length>1)return this.stack.pop()}closeAllNodes(){
for(;this.closeNode(););}toJSON(){return JSON.stringify(this.rootNode,null,4)}
walk(e){return this.constructor._walk(e,this.rootNode)}static _walk(e,n){
return"string"==typeof n?e.addText(n):n.children&&(e.openNode(n),
n.children.forEach((n=>this._walk(e,n))),e.closeNode(n)),e}static _collapse(e){
"string"!=typeof e&&e.children&&(e.children.every((e=>"string"==typeof e))?e.children=[e.children.join("")]:e.children.forEach((e=>{
l._collapse(e)})))}}class c extends l{constructor(e){super(),this.options=e}
addKeyword(e,n){""!==e&&(this.openNode(n),this.addText(e),this.closeNode())}
addText(e){""!==e&&this.add(e)}addSublanguage(e,n){const t=e.root
;t.kind=n,t.sublanguage=!0,this.add(t)}toHTML(){
return new o(this,this.options).value()}finalize(){return!0}}function d(e){
return e?"string"==typeof e?e:e.source:null}function g(e){return b("(?=",e,")")}
function u(e){return b("(?:",e,")?")}function b(...e){
return e.map((e=>d(e))).join("")}function m(...e){return"("+((e=>{
const n=e[e.length-1]
;return"object"==typeof n&&n.constructor===Object?(e.splice(e.length-1,1),n):{}
})(e).capture?"":"?:")+e.map((e=>d(e))).join("|")+")"}function p(e){
return RegExp(e.toString()+"|").exec("").length-1}
const _=/\[(?:[^\\\]]|\\.)*\]|\(\??|\\([1-9][0-9]*)|\\./
;function h(e,{joinWith:n}){let t=0;return e.map((e=>{t+=1;const n=t
;let a=d(e),i="";for(;a.length>0;){const e=_.exec(a);if(!e){i+=a;break}
i+=a.substring(0,e.index),
a=a.substring(e.index+e[0].length),"\\"===e[0][0]&&e[1]?i+="\\"+(Number(e[1])+n):(i+=e[0],
"("===e[0]&&t++)}return i})).map((e=>`(${e})`)).join(n)}
const f="[a-zA-Z]\\w*",E="[a-zA-Z_]\\w*",y="\\b\\d+(\\.\\d+)?",N="(-?)(\\b0[xX][a-fA-F0-9]+|(\\b\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)",w="\\b(0b[01]+)",v={
begin:"\\\\[\\s\\S]",relevance:0},O={scope:"string",begin:"'",end:"'",
illegal:"\\n",contains:[v]},M={scope:"string",begin:'"',end:'"',illegal:"\\n",
contains:[v]},x=(e,n,t={})=>{const a=s({scope:"comment",begin:e,end:n,
contains:[]},t);a.contains.push({scope:"doctag",
begin:"[ ]*(?=(TODO|FIXME|NOTE|BUG|OPTIMIZE|HACK|XXX):)",
end:/(TODO|FIXME|NOTE|BUG|OPTIMIZE|HACK|XXX):/,excludeBegin:!0,relevance:0})
;const i=m("I","a","is","so","us","to","at","if","in","it","on",/[A-Za-z]+['](d|ve|re|ll|t|s|n)/,/[A-Za-z]+[-][a-z]+/,/[A-Za-z][a-z]{2,}/)
;return a.contains.push({begin:b(/[ ]+/,"(",i,/[.]?[:]?([.][ ]|[ ])/,"){3}")}),a
},S=x("//","$"),k=x("/\\*","\\*/"),A=x("#","$");var C=Object.freeze({
__proto__:null,MATCH_NOTHING_RE:/\b\B/,IDENT_RE:f,UNDERSCORE_IDENT_RE:E,
NUMBER_RE:y,C_NUMBER_RE:N,BINARY_NUMBER_RE:w,
RE_STARTERS_RE:"!|!=|!==|%|%=|&|&&|&=|\\*|\\*=|\\+|\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\?|\\[|\\{|\\(|\\^|\\^=|\\||\\|=|\\|\\||~",
SHEBANG:(e={})=>{const n=/^#![ ]*\//
;return e.binary&&(e.begin=b(n,/.*\b/,e.binary,/\b.*/)),s({scope:"meta",begin:n,
end:/$/,relevance:0,"on:begin":(e,n)=>{0!==e.index&&n.ignoreMatch()}},e)},
BACKSLASH_ESCAPE:v,APOS_STRING_MODE:O,QUOTE_STRING_MODE:M,PHRASAL_WORDS_MODE:{
begin:/\b(a|an|the|are|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|they|like|more)\b/
},COMMENT:x,C_LINE_COMMENT_MODE:S,C_BLOCK_COMMENT_MODE:k,HASH_COMMENT_MODE:A,
NUMBER_MODE:{scope:"number",begin:y,relevance:0},C_NUMBER_MODE:{scope:"number",
begin:N,relevance:0},BINARY_NUMBER_MODE:{scope:"number",begin:w,relevance:0},
REGEXP_MODE:{begin:/(?=\/[^/\n]*\/)/,contains:[{scope:"regexp",begin:/\//,
end:/\/[gimuy]*/,illegal:/\n/,contains:[v,{begin:/\[/,end:/\]/,relevance:0,
contains:[v]}]}]},TITLE_MODE:{scope:"title",begin:f,relevance:0},
UNDERSCORE_TITLE_MODE:{scope:"title",begin:E,relevance:0},METHOD_GUARD:{
begin:"\\.\\s*[a-zA-Z_]\\w*",relevance:0},END_SAME_AS_BEGIN:e=>Object.assign(e,{
"on:begin":(e,n)=>{n.data._beginMatch=e[1]},"on:end":(e,n)=>{
n.data._beginMatch!==e[1]&&n.ignoreMatch()}})});function T(e,n){
"."===e.input[e.index-1]&&n.ignoreMatch()}function R(e,n){
void 0!==e.className&&(e.scope=e.className,delete e.className)}function D(e,n){
n&&e.beginKeywords&&(e.begin="\\b("+e.beginKeywords.split(" ").join("|")+")(?!\\.)(?=\\b|\\s)",
e.__beforeBegin=T,e.keywords=e.keywords||e.beginKeywords,delete e.beginKeywords,
void 0===e.relevance&&(e.relevance=0))}function I(e,n){
Array.isArray(e.illegal)&&(e.illegal=m(...e.illegal))}function L(e,n){
if(e.match){
if(e.begin||e.end)throw Error("begin & end are not supported with match")
;e.begin=e.match,delete e.match}}function B(e,n){
void 0===e.relevance&&(e.relevance=1)}const $=(e,n)=>{if(!e.beforeMatch)return
;if(e.starts)throw Error("beforeMatch cannot be used with starts")
;const t=Object.assign({},e);Object.keys(e).forEach((n=>{delete e[n]
})),e.keywords=t.keywords,e.begin=b(t.beforeMatch,g(t.begin)),e.starts={
relevance:0,contains:[Object.assign(t,{endsParent:!0})]
},e.relevance=0,delete t.beforeMatch
},z=["of","and","for","in","not","or","if","then","parent","list","value"]
;function F(e,n,t="keyword"){const a=Object.create(null)
;return"string"==typeof e?i(t,e.split(" ")):Array.isArray(e)?i(t,e):Object.keys(e).forEach((t=>{
Object.assign(a,F(e[t],n,t))})),a;function i(e,t){
n&&(t=t.map((e=>e.toLowerCase()))),t.forEach((n=>{const t=n.split("|")
;a[t[0]]=[e,j(t[0],t[1])]}))}}function j(e,n){
return n?Number(n):(e=>z.includes(e.toLowerCase()))(e)?0:1}const U={},P=e=>{
console.error(e)},K=(e,...n)=>{console.log("WARN: "+e,...n)},q=(e,n)=>{
U[`${e}/${n}`]||(console.log(`Deprecated as of ${e}. ${n}`),U[`${e}/${n}`]=!0)
},H=Error();function G(e,n,{key:t}){let a=0;const i=e[t],s={},r={}
;for(let e=1;e<=n.length;e++)r[e+a]=i[e],s[e+a]=!0,a+=p(n[e-1])
;e[t]=r,e[t]._emit=s,e[t]._multi=!0}function Z(e){(e=>{
e.scope&&"object"==typeof e.scope&&null!==e.scope&&(e.beginScope=e.scope,
delete e.scope)})(e),"string"==typeof e.beginScope&&(e.beginScope={
_wrap:e.beginScope}),"string"==typeof e.endScope&&(e.endScope={_wrap:e.endScope
}),(e=>{if(Array.isArray(e.begin)){
if(e.skip||e.excludeBegin||e.returnBegin)throw P("skip, excludeBegin, returnBegin not compatible with beginScope: {}"),
H
;if("object"!=typeof e.beginScope||null===e.beginScope)throw P("beginScope must be object"),
H;G(e,e.begin,{key:"beginScope"}),e.begin=h(e.begin,{joinWith:""})}})(e),(e=>{
if(Array.isArray(e.end)){
if(e.skip||e.excludeEnd||e.returnEnd)throw P("skip, excludeEnd, returnEnd not compatible with endScope: {}"),
H
;if("object"!=typeof e.endScope||null===e.endScope)throw P("endScope must be object"),
H;G(e,e.end,{key:"endScope"}),e.end=h(e.end,{joinWith:""})}})(e)}function W(e){
function n(n,t){return RegExp(d(n),"m"+(e.case_insensitive?"i":"")+(t?"g":""))}
class t{constructor(){
this.matchIndexes={},this.regexes=[],this.matchAt=1,this.position=0}
addRule(e,n){
n.position=this.position++,this.matchIndexes[this.matchAt]=n,this.regexes.push([n,e]),
this.matchAt+=p(e)+1}compile(){0===this.regexes.length&&(this.exec=()=>null)
;const e=this.regexes.map((e=>e[1]));this.matcherRe=n(h(e,{joinWith:"|"
}),!0),this.lastIndex=0}exec(e){this.matcherRe.lastIndex=this.lastIndex
;const n=this.matcherRe.exec(e);if(!n)return null
;const t=n.findIndex(((e,n)=>n>0&&void 0!==e)),a=this.matchIndexes[t]
;return n.splice(0,t),Object.assign(n,a)}}class a{constructor(){
this.rules=[],this.multiRegexes=[],
this.count=0,this.lastIndex=0,this.regexIndex=0}getMatcher(e){
if(this.multiRegexes[e])return this.multiRegexes[e];const n=new t
;return this.rules.slice(e).forEach((([e,t])=>n.addRule(e,t))),
n.compile(),this.multiRegexes[e]=n,n}resumingScanAtSamePosition(){
return 0!==this.regexIndex}considerAll(){this.regexIndex=0}addRule(e,n){
this.rules.push([e,n]),"begin"===n.type&&this.count++}exec(e){
const n=this.getMatcher(this.regexIndex);n.lastIndex=this.lastIndex
;let t=n.exec(e)
;if(this.resumingScanAtSamePosition())if(t&&t.index===this.lastIndex);else{
const n=this.getMatcher(0);n.lastIndex=this.lastIndex+1,t=n.exec(e)}
return t&&(this.regexIndex+=t.position+1,
this.regexIndex===this.count&&this.considerAll()),t}}
if(e.compilerExtensions||(e.compilerExtensions=[]),
e.contains&&e.contains.includes("self"))throw Error("ERR: contains `self` is not supported at the top-level of a language.  See documentation.")
;return e.classNameAliases=s(e.classNameAliases||{}),function t(i,r){const o=i
;if(i.isCompiled)return o
;[R,L,Z,$].forEach((e=>e(i,r))),e.compilerExtensions.forEach((e=>e(i,r))),
i.__beforeBegin=null,[D,I,B].forEach((e=>e(i,r))),i.isCompiled=!0;let l=null
;return"object"==typeof i.keywords&&i.keywords.$pattern&&(i.keywords=Object.assign({},i.keywords),
l=i.keywords.$pattern,
delete i.keywords.$pattern),l=l||/\w+/,i.keywords&&(i.keywords=F(i.keywords,e.case_insensitive)),
o.keywordPatternRe=n(l,!0),
r&&(i.begin||(i.begin=/\B|\b/),o.beginRe=n(i.begin),i.end||i.endsWithParent||(i.end=/\B|\b/),
i.end&&(o.endRe=n(i.end)),
o.terminatorEnd=d(i.end)||"",i.endsWithParent&&r.terminatorEnd&&(o.terminatorEnd+=(i.end?"|":"")+r.terminatorEnd)),
i.illegal&&(o.illegalRe=n(i.illegal)),
i.contains||(i.contains=[]),i.contains=[].concat(...i.contains.map((e=>(e=>(e.variants&&!e.cachedVariants&&(e.cachedVariants=e.variants.map((n=>s(e,{
variants:null},n)))),e.cachedVariants?e.cachedVariants:Q(e)?s(e,{
starts:e.starts?s(e.starts):null
}):Object.isFrozen(e)?s(e):e))("self"===e?i:e)))),i.contains.forEach((e=>{t(e,o)
})),i.starts&&t(i.starts,r),o.matcher=(e=>{const n=new a
;return e.contains.forEach((e=>n.addRule(e.begin,{rule:e,type:"begin"
}))),e.terminatorEnd&&n.addRule(e.terminatorEnd,{type:"end"
}),e.illegal&&n.addRule(e.illegal,{type:"illegal"}),n})(o),o}(e)}function Q(e){
return!!e&&(e.endsWithParent||Q(e.starts))}const X=i,V=s,J=Symbol("nomatch")
;var Y=(e=>{const n=Object.create(null),i=Object.create(null),s=[];let r=!0
;const o="Could not find the language '{}', did you forget to load/include a language module?",l={
disableAutodetect:!0,name:"Plain text",contains:[]};let d={
ignoreUnescapedHTML:!1,noHighlightRe:/^(no-?highlight)$/i,
languageDetectRe:/\blang(?:uage)?-([\w-]+)\b/i,classPrefix:"hljs-",
cssSelector:"pre code",languages:null,__emitter:c};function g(e){
return d.noHighlightRe.test(e)}function u(e,n,t){let a="",i=""
;"object"==typeof n?(a=e,
t=n.ignoreIllegals,i=n.language):(q("10.7.0","highlight(lang, code, ...args) has been deprecated."),
q("10.7.0","Please use highlight(code, options) instead.\nhttps://github.com/highlightjs/highlight.js/issues/2277"),
i=e,a=n),void 0===t&&(t=!0);const s={code:a,language:i};N("before:highlight",s)
;const r=s.result?s.result:b(s.language,s.code,t)
;return r.code=s.code,N("after:highlight",r),r}function b(e,t,i,s){
const l=Object.create(null);function c(){if(!M.keywords)return void S.addText(k)
;let e=0;M.keywordPatternRe.lastIndex=0;let n=M.keywordPatternRe.exec(k),t=""
;for(;n;){t+=k.substring(e,n.index)
;const i=w.case_insensitive?n[0].toLowerCase():n[0],s=(a=i,M.keywords[a]);if(s){
const[e,a]=s
;if(S.addText(t),t="",l[i]=(l[i]||0)+1,l[i]<=7&&(A+=a),e.startsWith("_"))t+=n[0];else{
const t=w.classNameAliases[e]||e;S.addKeyword(n[0],t)}}else t+=n[0]
;e=M.keywordPatternRe.lastIndex,n=M.keywordPatternRe.exec(k)}var a
;t+=k.substr(e),S.addText(t)}function g(){null!=M.subLanguage?(()=>{
if(""===k)return;let e=null;if("string"==typeof M.subLanguage){
if(!n[M.subLanguage])return void S.addText(k)
;e=b(M.subLanguage,k,!0,x[M.subLanguage]),x[M.subLanguage]=e._top
}else e=m(k,M.subLanguage.length?M.subLanguage:null)
;M.relevance>0&&(A+=e.relevance),S.addSublanguage(e._emitter,e.language)
})():c(),k=""}function u(e,n){let t=1;for(;void 0!==n[t];){if(!e._emit[t]){t++
;continue}const a=w.classNameAliases[e[t]]||e[t],i=n[t]
;a?S.addKeyword(i,a):(k=i,c(),k=""),t++}}function p(e,n){
return e.scope&&"string"==typeof e.scope&&S.openNode(w.classNameAliases[e.scope]||e.scope),
e.beginScope&&(e.beginScope._wrap?(S.addKeyword(k,w.classNameAliases[e.beginScope._wrap]||e.beginScope._wrap),
k=""):e.beginScope._multi&&(u(e.beginScope,n),k="")),M=Object.create(e,{parent:{
value:M}}),M}function _(e,n,t){let i=((e,n)=>{const t=e&&e.exec(n)
;return t&&0===t.index})(e.endRe,t);if(i){if(e["on:end"]){const t=new a(e)
;e["on:end"](n,t),t.isMatchIgnored&&(i=!1)}if(i){
for(;e.endsParent&&e.parent;)e=e.parent;return e}}
if(e.endsWithParent)return _(e.parent,n,t)}function h(e){
return 0===M.matcher.regexIndex?(k+=e[0],1):(R=!0,0)}function E(e){
const n=e[0],a=t.substr(e.index),i=_(M,e,a);if(!i)return J;const s=M
;M.endScope&&M.endScope._wrap?(g(),
S.addKeyword(n,M.endScope._wrap)):M.endScope&&M.endScope._multi?(g(),
u(M.endScope,e)):s.skip?k+=n:(s.returnEnd||s.excludeEnd||(k+=n),
g(),s.excludeEnd&&(k=n));do{
M.scope&&S.closeNode(),M.skip||M.subLanguage||(A+=M.relevance),M=M.parent
}while(M!==i.parent);return i.starts&&p(i.starts,e),s.returnEnd?0:n.length}
let y={};function N(n,s){const o=s&&s[0];if(k+=n,null==o)return g(),0
;if("begin"===y.type&&"end"===s.type&&y.index===s.index&&""===o){
if(k+=t.slice(s.index,s.index+1),!r){const n=Error(`0 width match regex (${e})`)
;throw n.languageName=e,n.badRule=y.rule,n}return 1}
if(y=s,"begin"===s.type)return(e=>{
const n=e[0],t=e.rule,i=new a(t),s=[t.__beforeBegin,t["on:begin"]]
;for(const t of s)if(t&&(t(e,i),i.isMatchIgnored))return h(n)
;return t.skip?k+=n:(t.excludeBegin&&(k+=n),
g(),t.returnBegin||t.excludeBegin||(k=n)),p(t,e),t.returnBegin?0:n.length})(s)
;if("illegal"===s.type&&!i){
const e=Error('Illegal lexeme "'+o+'" for mode "'+(M.scope||"<unnamed>")+'"')
;throw e.mode=M,e}if("end"===s.type){const e=E(s);if(e!==J)return e}
if("illegal"===s.type&&""===o)return 1
;if(T>1e5&&T>3*s.index)throw Error("potential infinite loop, way more iterations than matches")
;return k+=o,o.length}const w=f(e)
;if(!w)throw P(o.replace("{}",e)),Error('Unknown language: "'+e+'"')
;const v=W(w);let O="",M=s||v;const x={},S=new d.__emitter(d);(()=>{const e=[]
;for(let n=M;n!==w;n=n.parent)n.scope&&e.unshift(n.scope)
;e.forEach((e=>S.openNode(e)))})();let k="",A=0,C=0,T=0,R=!1;try{
for(M.matcher.considerAll();;){
T++,R?R=!1:M.matcher.considerAll(),M.matcher.lastIndex=C
;const e=M.matcher.exec(t);if(!e)break;const n=N(t.substring(C,e.index),e)
;C=e.index+n}return N(t.substr(C)),S.closeAllNodes(),S.finalize(),O=S.toHTML(),{
language:e,value:O,relevance:A,illegal:!1,_emitter:S,_top:M}}catch(n){
if(n.message&&n.message.includes("Illegal"))return{language:e,value:X(t),
illegal:!0,relevance:0,_illegalBy:{message:n.message,index:C,
context:t.slice(C-100,C+100),mode:n.mode,resultSoFar:O},_emitter:S};if(r)return{
language:e,value:X(t),illegal:!1,relevance:0,errorRaised:n,_emitter:S,_top:M}
;throw n}}function m(e,t){t=t||d.languages||Object.keys(n);const a=(e=>{
const n={value:X(e),illegal:!1,relevance:0,_top:l,_emitter:new d.__emitter(d)}
;return n._emitter.addText(e),n})(e),i=t.filter(f).filter(y).map((n=>b(n,e,!1)))
;i.unshift(a);const s=i.sort(((e,n)=>{
if(e.relevance!==n.relevance)return n.relevance-e.relevance
;if(e.language&&n.language){if(f(e.language).supersetOf===n.language)return 1
;if(f(n.language).supersetOf===e.language)return-1}return 0})),[r,o]=s,c=r
;return c.secondBest=o,c}function p(e){let n=null;const t=(e=>{
let n=e.className+" ";n+=e.parentNode?e.parentNode.className:""
;const t=d.languageDetectRe.exec(n);if(t){const n=f(t[1])
;return n||(K(o.replace("{}",t[1])),
K("Falling back to no-highlight mode for this block.",e)),n?t[1]:"no-highlight"}
return n.split(/\s+/).find((e=>g(e)||f(e)))})(e);if(g(t))return
;N("before:highlightElement",{el:e,language:t
}),!d.ignoreUnescapedHTML&&e.children.length>0&&(console.warn("One of your code blocks includes unescaped HTML. This is a potentially serious security risk."),
console.warn("https://github.com/highlightjs/highlight.js/issues/2886"),
console.warn(e)),n=e;const a=n.textContent,s=t?u(a,{language:t,ignoreIllegals:!0
}):m(a);e.innerHTML=s.value,((e,n,t)=>{const a=n&&i[n]||t
;e.classList.add("hljs"),e.classList.add("language-"+a)
})(e,t,s.language),e.result={language:s.language,re:s.relevance,
relevance:s.relevance},s.secondBest&&(e.secondBest={
language:s.secondBest.language,relevance:s.secondBest.relevance
}),N("after:highlightElement",{el:e,result:s,text:a})}let _=!1;function h(){
"loading"!==document.readyState?document.querySelectorAll(d.cssSelector).forEach(p):_=!0
}function f(e){return e=(e||"").toLowerCase(),n[e]||n[i[e]]}
function E(e,{languageName:n}){"string"==typeof e&&(e=[e]),e.forEach((e=>{
i[e.toLowerCase()]=n}))}function y(e){const n=f(e)
;return n&&!n.disableAutodetect}function N(e,n){const t=e;s.forEach((e=>{
e[t]&&e[t](n)}))}
"undefined"!=typeof window&&window.addEventListener&&window.addEventListener("DOMContentLoaded",(()=>{
_&&h()}),!1),Object.assign(e,{highlight:u,highlightAuto:m,highlightAll:h,
highlightElement:p,
highlightBlock:e=>(q("10.7.0","highlightBlock will be removed entirely in v12.0"),
q("10.7.0","Please use highlightElement now."),p(e)),configure:e=>{d=V(d,e)},
initHighlighting:()=>{
h(),q("10.6.0","initHighlighting() deprecated.  Use highlightAll() now.")},
initHighlightingOnLoad:()=>{
h(),q("10.6.0","initHighlightingOnLoad() deprecated.  Use highlightAll() now.")
},registerLanguage:(t,a)=>{let i=null;try{i=a(e)}catch(e){
if(P("Language definition for '{}' could not be registered.".replace("{}",t)),
!r)throw e;P(e),i=l}
i.name||(i.name=t),n[t]=i,i.rawDefinition=a.bind(null,e),i.aliases&&E(i.aliases,{
languageName:t})},unregisterLanguage:e=>{delete n[e]
;for(const n of Object.keys(i))i[n]===e&&delete i[n]},
listLanguages:()=>Object.keys(n),getLanguage:f,registerAliases:E,
autoDetection:y,inherit:V,addPlugin:e=>{(e=>{
e["before:highlightBlock"]&&!e["before:highlightElement"]&&(e["before:highlightElement"]=n=>{
e["before:highlightBlock"](Object.assign({block:n.el},n))
}),e["after:highlightBlock"]&&!e["after:highlightElement"]&&(e["after:highlightElement"]=n=>{
e["after:highlightBlock"](Object.assign({block:n.el},n))})})(e),s.push(e)}
}),e.debugMode=()=>{r=!1},e.safeMode=()=>{r=!0},e.versionString="11.2.0"
;for(const e in C)"object"==typeof C[e]&&t(C[e]);return Object.assign(e,C),e
})({});const Fe=Y;
return Fe}()
;"object"==typeof exports&&"undefined"!=typeof module&&(module.exports=hljs);