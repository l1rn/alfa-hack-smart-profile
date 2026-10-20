# Bookmarklet: сохранить страницу в .md

Для curated KB: открыл продукт на alfabank.ru → закладка → `.md` в «Загрузки».

## Как поставить

1. Создай закладку (Chrome: закладки → добавить).
2. В **URL** вставь код из блока ниже целиком (`javascript:...`).
3. Имя: `Save page MD`.

## Закладка (скопировать целиком)

```
javascript:(()=>{try{const kill='nav,header,footer,aside,[role="navigation"],[role="banner"],[role="contentinfo"],script,style,noscript,iframe,svg';const clone=document.body.cloneNode(true);clone.querySelectorAll(kill).forEach(n=>n.remove());const pick=()=>{const sels=['main','article','[role="main"]','.content','.page-content','#content'];for(const s of sels){const el=document.querySelector(s);if(el&&el.innerText&&el.innerText.trim().length>200)return el.innerText.trim()}return(clone.innerText||'').trim()};const title=(document.title||'page').trim();const url=location.href;const text=pick();const now=new Date().toISOString();const md=['---',`title: ${JSON.stringify(title)}`,`url: ${JSON.stringify(url)}`,`saved_at: ${JSON.stringify(now)}`,`source_host: ${JSON.stringify(location.host)}`,'---','',`# ${title}`,'',`> Источник: ${url}`,'',text,''].join('\n');const safe=title.toLowerCase().replace(/[^\p{L}\p{N}]+/gu,'-').replace(/^-|-$/g,'').slice(0,80)||'page';const name=`${safe}-${now.slice(0,10)}.md`;const blob=new Blob([md],{type:'text/markdown;charset=utf-8'});const a=document.createElement('a');a.href=URL.createObjectURL(blob);a.download=name;document.body.appendChild(a);a.click();a.remove();URL.revokeObjectURL(a.href);console.log('[save-page]',name,text.length+' chars')}catch(e){alert('Save failed: '+e.message)}})();
```

## Как пользоваться

1. Дождись полной загрузки (антибот/спиннер прошли).
2. Проскролль страницу.
3. **Раскрой аккордеоны FAQ** («Требования», «Документы», «Как нельзя…») — иначе ответы не попадут в текст.
4. Жми закладку → файл в «Загрузки» → кидай в `discovery/alfa_site/`.
5. Потом можно прогнать `py -3 discovery/_clean_alfa_dumps.py` (обрежет хвосты нав/футера и переименует по slug URL).

## Ограничения

- Работает только когда страница уже открыта у тебя в браузере.
- SPA: если блок ещё не дорисовался — скроллни и жми снова.
- PDF не сохраняет.
