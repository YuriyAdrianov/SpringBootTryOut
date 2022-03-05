<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
        <span><a href="/user">User List</a> </span>
    </div>
    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Enter a message"/>
            <input type="text" name="tag" placeholder="Enter a tag">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
        </form>
    </div>

    <div>Message List:</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}">
        <button type="submit">Find</button>
    </form>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Tag</th>
            <th>Author</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list messages as message>
            <tr>
                <td><b>${message.id}</b></td>
                <td><span>${message.text}</span></td>
                <td><b>${message.tag}</b></td>
                <td><strong>${message.authorName}</strong></td>
            </tr>
        <#else>
            No messages
        </#list>
        </tbody>
    </table>
</@c.page>