const heading = document.querySelector('#modal-title');

class Page extends CRUDPage
{
    constructor(btnAdd, table, form)
    { super('/api/misc/brand', btnAdd, table, form); }

    init()
    {
        super.init();
        this.btnAdd.onclick = () => {
            this.form.reset();
            this.form.onsubmit(this.create.bind(this))
            
            heading.textContent = 'Insertion Marque';
        };
    }

    render(data, page)
    {
        const tbody = table.querySelector('tbody');
        tbody.replaceChildren();

        for(const row of data.page)
        {
            tbody.append(tag('tr', {}, [
                tag('td', {'class': 'd-flex gap-1'}, [
                    BtnUpdate(() => {
                        heading.textContent = 'Modification Marque';
                        this.form.load({
                            'label': row.label,
                        });
                        this.form.onsubmit(this.update.bind(this, row.id));
                    }),
                    BtnDelete(this.delete.bind(this, row.id)),
                ]),
                tag('td', {}, [text(row.label)]),
            ]));
        }
        
        const pagination = new Pagination(
            document.querySelector('.pagination'),
            PaginationPage(),
            data.count,
            this.read.bind(this)
        );
        pagination.init();
        pagination.setActive(page);
    }
}

window.addEventListener('load', e => {
    new Page(
        document.getElementById('btn-add'),
        document.getElementById('table'),
        document.getElementById('form')
    ).init()
})