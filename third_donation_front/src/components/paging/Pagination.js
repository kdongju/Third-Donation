const Pagination = ({ totalPage, curPage, fetch }) => {
  const items = [];

  const paginate = (pageNumber) => fetch(pageNumber);
  const nextPaginate = () => curPage < totalPage - 1 && fetch(curPage + 1);
  const prevPaginate = () => curPage > 0 && fetch(curPage - 1);
  const lastPaginate = () => fetch(totalPage - 1);
  const firstPaginate = () => fetch(0);

  for (let number = 1; number <= totalPage; number++) {
    items.push(
      <li
        key={number}
        onClick={() => paginate(number - 1)}
        className={number - 1 == curPage ? 'active' : null}>
        <span className="a">{number}</span>
      </li>,
    );
  }

  return (
    <div className="d-flex justify-content-center">
      <ul className="pagination">
        <li onClick={firstPaginate}>
          <span className="a">
            <i className="fa fa-angle-double-left"></i>
          </span>
        </li>
        <li onClick={prevPaginate}>
          <span className="a">
            <i className="fa fa-angle-left"></i>
          </span>
        </li>
        {items}
        <li onClick={nextPaginate}>
          <span className="a">
            <i className="fa fa-angle-right"></i>
          </span>
        </li>
        <li onClick={lastPaginate}>
          <span className="a">
            <i className="fa fa-angle-double-right"></i>
          </span>
        </li>
        {/* <li onClick={firstPaginate} className="page-link">
          &laquo;
        </li>
        <li onClick={prevPaginate} className="page-link">
          &lt;
        </li>
        {items}
        <li onClick={nextPaginate} className="page-link">
          &gt;
        </li>
        <li onClick={lastPaginate} className="page-link">
          &raquo;
        </li> */}
      </ul>
    </div>
  );
};
export default Pagination;
