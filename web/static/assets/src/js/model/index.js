const heading = document.querySelector('#modal-title');

class Page extends CRUDPage
{
    constructor(btnAdd, table, form)
    { super('/api/misc/model', btnAdd, table, form); }

    init()
    {
        super.init();
        this.btnAdd.onclick = () => {
            this.form.reset();
            this.form.onsubmit(this.create.bind(this))
            
            heading.textContent = 'Insertion de Modèle';
        };
    }

    render(data, page)
    {
        const tbody = table.querySelector('tbody');
        tbody.replaceChildren();

        for(const row of data.content.page)
        {
            tbody.append(tag('tr', {}, [
                tag('td', {'class': 'd-flex gap-1'}, [
                    BtnUpdate(() => {
                        heading.textContent = 'Modification de Modèle';
                        this.form.load(row);
                        this.form.onsubmit(this.update.bind(this, row.id));
                    }),
                    BtnDelete(this.delete.bind(this, row.id)),
                ]),
                tag('td', {}, [text(row.serialNumber)]),
                tag('td', {}, [text(data.modelCategories[row.idModelCategory])]),
                tag('td', {}, [text(data.brands[row.idBrand])]),
                tag('td', {}, [text(row.description)]),
            ]));
        }
        
        const pagination = new Pagination(
            document.querySelector('.pagination'),
            PaginationPage(),
            data.content.count,
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